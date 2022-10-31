package by.it_academy.seabattle.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public final class TextInterface {

    private final Collection<Command> commands;

    public TextInterface(Collection<Command> commands) {
        this.commands = commands;
    }

    public String execute(UUID playerId, String input) {
        return commands.stream()
                .filter(command -> input.startsWith(command.name()))
                .map(command -> command.execute(playerId, arguments(input, command.name())))
                .findAny()
                .orElse("Unknown command");
    }

    private List<String> arguments(String input, String commandName) {
        return Arrays.asList(
                input.substring(commandName.length())
                        .trim()
                        .split(" ")
        );
    }
}
