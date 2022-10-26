package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

class TelegramBot extends TelegramLongPollingBot {

    private final SeaBattle seaBattle;
    private final Map<Long, UUID> playerIdMap = new HashMap<>();

    TelegramBot(SeaBattle seaBattle) {
        this.seaBattle = seaBattle;
    }

    public static void main(String[] args) throws TelegramApiException {
        SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates());
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new TelegramBot(seaBattle));
        new ConsoleRunner(seaBattle).run();
    }

    @Override
    public String getBotUsername() {
        return "sea_battle_it_academy_bot";
    }

    @Override
    public String getBotToken() {
        return "5655608712:AAE1rzYPPddVXVUsb2ewCkd2bh9UqAG67ao";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String command = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if (command.equals("/start")) {
                UUID playerId = UUID.randomUUID();
                seaBattle.registerNewPlayerUseCase().register(playerId);
                playerIdMap.put(chatId, playerId);
                respond(chatId, "Your ID is " + playerId);
                return;
            }
            if (command.contains("queue")) {
                seaBattle.addPlayerToQueueUseCase().add(playerIdMap.get(chatId));
            } else if (command.contains("boards")) {
                try {
                    BoardsQuery.Boards boards = seaBattle.boardsQuery().getCurrentBoards(playerIdMap.get(chatId));
                    respond(chatId, boards.toString());
                } catch (GameWasNotFound e) {
                    respond(chatId, "Game was not found");
                }
            } else if (command.contains("place")) {
                List<String> coordinates = Arrays.stream(command.substring(5).trim().split(" "))
                        .toList();
                try {
                    seaBattle.placeShipUseCase().place(playerIdMap.get(chatId), coordinates);
                } catch (GameWasNotFound e) {
                    respond(chatId, "Game was not found");
                } catch (CoordinatesIsNotValid | ShipIsNotValid e) {
                    respond(chatId, "invalid input");
                }
            } else if (command.contains("shoot")) {
                String coordinates = command.substring(5).trim();
                try {
                    seaBattle.shootUseCase().shoot(playerIdMap.get(chatId), coordinates);
                } catch (GameWasNotFound e) {
                    respond(chatId, "Game was not found");
                } catch (BattleHasNotBeenStarted e) {
                    respond(chatId, "Battle has not been started");
                } catch (CoordinatesIsNotValid e) {
                    respond(chatId, "invalid input");
                } catch (NotYourTurn e) {
                    respond(chatId, "Not your turn");
                }
            }
        }
    }

    private void respond(Long chatId, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(response);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

