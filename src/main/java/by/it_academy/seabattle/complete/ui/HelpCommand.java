package by.it_academy.seabattle.complete.ui;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class HelpCommand implements Command {

    private final Collection<Command> commands;

    public HelpCommand(Collection<Command> commands) {
        this.commands = List.copyOf(commands);
    }

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String help() {
        return "Show this help";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        return commands.stream()
                .map(command -> command.name() + " - " + command.help())
                .collect(
                        Collectors.joining(
                                System.lineSeparator(),
                                "List of available commands:" + System.lineSeparator(),
                                ""
                        )
                );
    }
}
