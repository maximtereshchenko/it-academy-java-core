package by.it_academy.seabattle.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class TextInterface {

    private final Collection<Command> commands;

    public TextInterface(Collection<Command> commands) {
        this.commands = List.copyOf(commands);
    }

    public String execute(PlayerIdStorage playerIdStorage, String input) {
        return commands.stream()
                .filter(command -> input.startsWith(command.name()))
                .map(command -> command.execute(playerIdStorage, arguments(input, command.name())))
                .findAny()
                .orElse("Unknown command");
    }

    private List<String> arguments(String input, String commandName) {
        return Arrays.stream(
                        input.substring(commandName.length())
                                .trim()
                                .split(" ")
                )
                .filter(argument -> !argument.isEmpty())
                .toList();
    }
}
