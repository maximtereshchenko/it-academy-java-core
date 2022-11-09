package by.it_academy.seabattle.complete.ui;

import java.util.List;

public interface Command {

    String name();

    String help();

    String execute(PlayerIdStorage playerIdStorage, List<String> arguments);
}
