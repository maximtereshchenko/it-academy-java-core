package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.util.Set;
import java.util.UUID;

interface Grid {

    UUID ownerId();

    boolean isOwnedBy(Player player);

    GameStates.Grid state();

    Set<GridsQuery.SquareView> view(Player player);

    SquareQuery.Status square(Square square);
}
