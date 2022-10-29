package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

interface Game {

    GameStates.State state();

    GridsQuery.Grids view(Player player);
}
