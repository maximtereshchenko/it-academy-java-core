package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

import java.util.Set;
import java.util.UUID;

interface Grid {

    UUID ownerId();

    boolean isOwnedBy(Player player);

    GameStates.Grid state();

    Set<GridsQuery.SquareView> view(Player player);
}
