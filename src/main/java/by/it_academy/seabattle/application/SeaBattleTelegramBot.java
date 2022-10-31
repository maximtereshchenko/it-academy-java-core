package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.ui.RegisterCommand;
import by.it_academy.seabattle.ui.TextInterface;
import by.it_academy.seabattle.usecase.SquareQuery;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

final class SeaBattleTelegramBot extends TelegramLongPollingBot {

    private final Chats chats;
    private final TextInterface textInterface;

    SeaBattleTelegramBot(Chats chats, TextInterface textInterface, SeaBattle seaBattle) {
        this.chats = chats;
        this.textInterface = textInterface;
        seaBattle.addGameStartedObserverUseCase().add(this::onGameStarted);
        seaBattle.addPlayerShotObserverUseCase()
                .add((shotOwnerId, targetGridOwnerId, coordinates) ->
                        onShot(shotOwnerId, targetGridOwnerId, coordinates, seaBattle.squareQuery())
                );
        seaBattle.addGameOverObserverUseCase().add(this::onGameOver);
        seaBattle.addAllShipsPositionedObserverUseCase().add(this::onAllShipsPositioned);
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
        long chatId = message.getChat().getId();
        String input = input(message);
        String result = textInterface.execute(chats.playerId(chatId).orElse(null), input);
        if (input.equals(RegisterCommand.NAME)) {
            chats.save(chatId, UUID.fromString(result));
        }
        respond(chatId, result);
    }

    private void onAllShipsPositioned(UUID firstPlayerId, UUID secondPlayerId) {
        Stream.of(firstPlayerId, secondPlayerId)
                .map(chats::chatId)
                .flatMap(Optional::stream)
                .forEach(chatId -> respond(chatId, "All ships positioned!"));
    }

    private void onGameOver(UUID winnerId, UUID loserId) {
        Stream.of(winnerId, loserId)
                .map(chats::chatId)
                .flatMap(Optional::stream)
                .forEach(chatId -> respond(chatId, "Game is over!"));
    }

    private void onShot(UUID shotOwnerId, UUID targetGridOwnerId, String coordinates, SquareQuery squareQuery) {
        chats.chatId(targetGridOwnerId)
                .ifPresent(chatId ->
                        respond(
                                chatId,
                                "Opponent shot at %s - %s!".formatted(
                                        coordinates,
                                        shotResult(shotOwnerId, coordinates, squareQuery)
                                )
                        )
                );
    }

    private String shotResult(UUID shotOwnerId, String coordinates, SquareQuery squareQuery) {
        SquareQuery.Status status = squareQuery.square(shotOwnerId, coordinates);
        if (status == SquareQuery.Status.DESTROYED_SHIP_SEGMENT) {
            return "Hit";
        }
        if (status == SquareQuery.Status.CHECKED) {
            return "Miss";
        }
        return "";
    }

    private void onGameStarted(UUID firstPlayerId, UUID secondPlayerId) {
        Stream.of(firstPlayerId, secondPlayerId)
                .map(chats::chatId)
                .flatMap(Optional::stream)
                .forEach(chatId -> respond(chatId, "Game has been started!"));
    }

    private String input(Message message) {
        String text = message.getText().toLowerCase(Locale.ROOT);
        if (message.isCommand() && text.equals("/start")) {
            return RegisterCommand.NAME;
        }
        return text;
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
