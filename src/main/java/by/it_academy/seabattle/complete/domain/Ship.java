package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.util.Set;

interface Ship {

    Ship hit(Square square);

    boolean positionedOn(Square square);

    boolean isSunk();

    int length();

    GameStates.Ship state();

    Set<Square> neighbourSquares();

    Set<GridsQuery.SquareView> view();

    SquareQuery.Status square(Square square);
}
