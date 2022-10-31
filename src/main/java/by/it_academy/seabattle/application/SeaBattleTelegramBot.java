package by.it_academy.seabattle.application;

import by.it_academy.seabattle.ui.RegisterCommand;
import by.it_academy.seabattle.ui.TextInterface;
import by.it_academy.seabattle.usecase.AddGameStartedObserverUseCase;
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

    SeaBattleTelegramBot(Chats chats, TextInterface textInterface, AddGameStartedObserverUseCase useCase) {
        this.chats = chats;
        this.textInterface = textInterface;
        useCase.add(this::onGameStarted);
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
