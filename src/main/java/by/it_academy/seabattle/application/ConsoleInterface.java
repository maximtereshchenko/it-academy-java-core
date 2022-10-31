package by.it_academy.seabattle.application;

import by.it_academy.seabattle.ui.RegisterCommand;
import by.it_academy.seabattle.ui.TextInterface;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.UUID;

final class ConsoleInterface implements Runnable {

    private final TextInterface textInterface;
    private UUID playerId;

    ConsoleInterface(TextInterface textInterface) {
        this.textInterface = textInterface;
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
}
