package by.it_academy.seabattle.ui;

import java.util.List;

public interface Command {

    String name();

    String help();

    String execute(PlayerIdStorage playerIdStorage, List<String> arguments);
}
