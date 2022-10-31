package by.it_academy.seabattle.application;

import by.it_academy.seabattle.ui.RegisterCommand;
import by.it_academy.seabattle.ui.TextInterface;
import by.it_academy.seabattle.usecase.AddGameStartedObserverUseCase;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

final class ConsoleInterface implements Runnable {

    private final TextInterface textInterface;
    private UUID playerId;

    ConsoleInterface(TextInterface textInterface, AddGameStartedObserverUseCase useCase) {
        this.textInterface = textInterface;
        useCase.add(this::onGameStarted);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String result = textInterface.execute(playerId, input);
            if (input.equals(RegisterCommand.NAME)) {
                playerId = UUID.fromString(result);
            }
            System.out.println(result);
        }
    }

    private void onGameStarted(UUID firstPlayerId, UUID secondPlayerId) {
        if (!firstPlayerId.equals(playerId) && !secondPlayerId.equals(playerId)) {
            return;
        }
        System.out.println("Game has been started!");
    }
}
