package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

final class ConsoleRunner extends Thread {

    private final SeaBattle seaBattle;

    ConsoleRunner(SeaBattle seaBattle) {
        this.seaBattle = seaBattle;
    }

    @Override
    public void run() {
        UUID playerId = UUID.randomUUID();
        seaBattle.registerNewPlayerUseCase().register(playerId);
        System.out.println("Your ID is " + playerId);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String command = scanner.nextLine();
            if (command.contains("queue")) {
                seaBattle.addPlayerToQueueUseCase().add(playerId);
            } else if (command.contains("boards")) {
                try {
                    BoardsQuery.Boards boards = seaBattle.boardsQuery().getCurrentBoards(playerId);
                    System.out.println(boards);
                } catch (GameWasNotFound e) {
                    System.out.println("Game was not found");
                }
            } else if (command.contains("place")) {
                List<String> coordinates = Arrays.stream(command.substring(5).trim().split(" "))
                        .toList();
                try {
                    seaBattle.placeShipUseCase().place(playerId, coordinates);
                } catch (GameWasNotFound e) {
                    System.out.println("Game was not found");
                } catch (CoordinatesIsNotValid | ShipIsNotValid e) {
                    System.out.println("invalid input");
                }
            } else if (command.contains("shoot")) {
                String coordinates = command.substring(5).trim();
                try {
                    seaBattle.shootUseCase().shoot(playerId, coordinates);
                } catch (GameWasNotFound e) {
                    System.out.println("Game was not found");
                } catch (BattleHasNotBeenStarted e) {
                    System.out.println("Battle has not been started");
                } catch (CoordinatesIsNotValid e) {
                    System.out.println("invalid input");
                } catch (NotYourTurn e) {
                    System.out.println("Not your turn");
                }
            }
        }
    }
}
