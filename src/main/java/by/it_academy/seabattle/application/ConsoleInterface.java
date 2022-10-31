package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.ui.RegisterCommand;
import by.it_academy.seabattle.ui.TextInterface;
import by.it_academy.seabattle.usecase.SquareQuery;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

final class ConsoleInterface implements Runnable {

    private final TextInterface textInterface;
    private UUID playerId;

    ConsoleInterface(TextInterface textInterface, SeaBattle seaBattle) {
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

    private void onAllShipsPositioned(UUID firstPlayerId, UUID secondPlayerId) {
        if (!firstPlayerId.equals(playerId) && !secondPlayerId.equals(playerId)) {
            return;
        }
        System.out.println("All ships positioned!");
    }

    private void onGameStarted(UUID firstPlayerId, UUID secondPlayerId) {
        if (!firstPlayerId.equals(playerId) && !secondPlayerId.equals(playerId)) {
            return;
        }
        System.out.println("Game has been started!");
    }

    private void onShot(UUID shotOwnerId, UUID targetGridOwnerId, String coordinates, SquareQuery squareQuery) {
        if (!targetGridOwnerId.equals(playerId)) {
            return;
        }
        System.out.printf("Opponent shot at %s - %s!%n", coordinates, shotResult(shotOwnerId, coordinates, squareQuery));
    }

    private void onGameOver(UUID winnerId, UUID loserId) {
        if (!winnerId.equals(playerId) && !loserId.equals(playerId)) {
            return;
        }
        System.out.println("Game is over!");
    }
}
