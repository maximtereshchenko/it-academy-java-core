package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

final class SeaBattleTelegramBot extends TelegramLongPollingBot {

    private static final String START = "/start";
    private static final String QUEUE = "queue";
    private static final String POSITION = "position";
    private static final String FILL = "fill";
    private static final String SHOOT = "shoot";
    private static final String GRIDS = "grids";

    private final Chats chats;
    private final SeaBattle seaBattle;

    SeaBattleTelegramBot(Chats chats, SeaBattle seaBattle) {
        this.chats = chats;
        this.seaBattle = seaBattle;
    }

    public static void main(String[] args) throws TelegramApiException {
        new TelegramBotsApi(DefaultBotSession.class)
                .registerBot(
                        new SeaBattleTelegramBot(
                                new InMemoryChats(),
                                new SeaBattle(new InMemoryPlayerIds(), new InMemoryGameStates())
                        )
                );
    }

    @Override
    public String getBotUsername() {
        return "sea_battle_it_academy_bot";
    }

    @Override
    public String getBotToken() {
        try {
            return Files.readString(Paths.get("src/main/resources/bot_token.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }
        Message message = update.getMessage();
        String text = message.getText().toLowerCase(Locale.ROOT);
        long chatId = message.getChat().getId();
        if (message.isCommand() && text.equals(START)) {
            registerPlayer(chatId);
        }
        if (text.equals(QUEUE)) {
            addPlayerToQueue(chatId);
        }
        if (text.startsWith(POSITION + " ")) {
            positionShip(chatId, text);
        }
        if (text.equals(FILL)) {
            fillGrid(chatId);
        }
        if (text.startsWith(SHOOT + " ")) {
            shoot(chatId, text);
        }
        if (text.equals(GRIDS)) {
            printGrids(chatId);
        }
    }

    private void printGrids(long chatId) {
        try {
            GridsQuery.Grids grids = seaBattle.boardsQuery().currentGrids(chats.playerId(chatId));
            if (grids.phase() == GridsQuery.Phase.SHIP_POSITIONING) {
                respond(chatId, "Waiting for all ships positioned");
            }
            if (grids.phase() == GridsQuery.Phase.BATTLE) {
                respond(chatId, "Battle is on");
            }
            if (grids.phase() == GridsQuery.Phase.OVER) {
                respond(chatId, "Game is over");
            }
            if (grids.phase() != GridsQuery.Phase.SHIP_POSITIONING) {
                respond(
                        chatId,
                        grids.winner()
                                .map(winner -> "Winner is " + winner)
                                .orElseGet(() -> nextTurnMessage(chats.playerId(chatId), grids.turnOwnerId()))
                );
            }
            respond(chatId, "Your board:" + System.lineSeparator() + toString(grids.ownedSquares()));
            respond(chatId, "Opponents board:" + System.lineSeparator() + toString(grids.opponentSquares()));
        } catch (GameWasNotFound e) {
            respondGameWasNotFound(chatId);
        }
    }

    private String toString(Set<GridsQuery.SquareView> squareViews) {
        StringBuilder builder = new StringBuilder("<pre>")
                .append(System.lineSeparator())
                .append("   ABCDEFGHIJ")
                .append(System.lineSeparator())
                .append("  -----------")
                .append(System.lineSeparator());
        for (int row = 1; row <= 10; row++) {
            if (row < 10) {
                builder.append(' ');
            }
            builder.append(row).append('|');
            for (char column = 'a'; column <= 'j'; column++) {
                builder.append(
                        findStatus(squareViews, column, row)
                                .map(this::toString)
                                .orElse(' ')
                );
            }
            builder.append(System.lineSeparator());
        }
        return builder.append("</pre>").toString();
    }

    private char toString(GridsQuery.Status status) {
        switch (status) {
            case SHIP_SEGMENT:
                return 'o';
            case DESTROYED_SHIP_SEGMENT:
                return '*';
            case EMPTY:
                return 'x';
            default:
                return ' ';
        }
    }

    private Optional<GridsQuery.Status> findStatus(Set<GridsQuery.SquareView> squareViews, char column, int row) {
        return squareViews.stream()
                .filter(squareView -> squareView.column() == column)
                .filter(squareView -> squareView.row() == row)
                .map(GridsQuery.SquareView::status)
                .findAny();
    }

    private String nextTurnMessage(UUID playerId, UUID turnOwnerId) {
        if (turnOwnerId.equals(playerId)) {
            return "It is your turn";
        }
        return "It is opponent's turn";
    }

    private void shoot(long chatId, String text) {
        try {
            seaBattle.shootUseCase().shoot(chats.playerId(chatId), text.substring(SHOOT.length()).trim());
            respond(chatId, "Shot was fired");
        } catch (GameWasNotFound e) {
            respondGameWasNotFound(chatId);
        } catch (SquareIsNotValid e) {
            respond(chatId, "Square is not valid");
        } catch (NotYourTurn e) {
            respond(chatId, "It is not your turn");
        }
    }

    private void respondGameWasNotFound(long chatId) {
        respond(chatId, "Game was not found");
    }

    private void fillGrid(long chatId) {
        try {
            seaBattle.fillGridWithRandomShipsUseCase().fill(chats.playerId(chatId));
            respond(chatId, "Ships positioned");
        } catch (GridIsComplete e) {
            respond(chatId, "Grid is complete");
        }
    }

    private void positionShip(long chatId, String text) {
        try {
            seaBattle.positionShipUseCase()
                    .position(
                            chats.playerId(chatId),
                            Arrays.stream(
                                            text.substring(POSITION.length() + 1)
                                                    .split(" ")
                                    )
                                    .filter(coordinates -> !coordinates.isEmpty())
                                    .toList()
                    );
            respond(chatId, "Ship positioned");
        } catch (GameWasNotFound e) {
            respondGameWasNotFound(chatId);
        } catch (ShipIsNotValid e) {
            respond(chatId, "Ship is not valid");
        } catch (SquareIsNotValid e) {
            respond(chatId, "Square is not valid");
        }
    }

    private void addPlayerToQueue(long chatId) {
        try {
            seaBattle.addPlayerToQueueUseCase().add(chats.playerId(chatId));
            respond(chatId, "You has been added to queue. Please wait");
        } catch (PlayerIsInQueue e) {
            respond(chatId, "You are already in queue");
        } catch (GameWithPlayerExists e) {
            respond(chatId, "Game exists");
        }
    }

    private void registerPlayer(long chatId) {
        while (true) {
            UUID playerId = UUID.randomUUID();
            try {
                seaBattle.registerNewPlayerUseCase().register(playerId);
                chats.save(chatId, playerId);
                respond(chatId, "Your ID is " + playerId);
                return;
            } catch (DuplicatePlayerId ignored) {
            }
        }
    }

    private void respond(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setParseMode("HTML");
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
