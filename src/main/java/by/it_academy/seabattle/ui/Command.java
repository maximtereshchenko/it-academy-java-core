package by.it_academy.seabattle.ui;

import java.util.List;
import java.util.UUID;

public interface Command {

    String name();

    String help();

    String execute(UUID playerId, List<String> arguments);
}
