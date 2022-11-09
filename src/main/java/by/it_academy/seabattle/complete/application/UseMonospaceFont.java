package by.it_academy.seabattle.complete.application;

import by.it_academy.seabattle.complete.ui.Command;
import by.it_academy.seabattle.complete.ui.PlayerIdStorage;

import java.util.List;

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
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        return "<pre>" + original.execute(playerIdStorage, arguments) + "</pre>";
    }
}
