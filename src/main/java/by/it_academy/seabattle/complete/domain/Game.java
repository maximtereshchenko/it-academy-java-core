package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.util.UUID;

interface Game {

    UUID firstPlayerId();

    UUID secondPlayerId();

    GameStates.State state();

    GridsQuery.Grids view(Player player);

    boolean isNotOver();

    boolean hasNotAllShips();

    SquareQuery.Status square(Player player, Square square);
}
