package by.it_academy.seabattle.application;

import by.it_academy.seabattle.ui.Command;

import java.util.List;
import java.util.UUID;

final class UseMonospaceFont implements Command {

    private final Command original;

    UseMonospaceFont(Command original) {
        this.original = original;
    }

    @Override
    public String name() {
        return original.name();
    }

    @Override
    public String help() {
        return original.help();
    }

    @Override
    public String execute(UUID playerId, List<String> arguments) {
        return "<pre>" + original.execute(playerId, arguments) + "</pre>";
    }
}
