package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;

import java.util.Collection;
import java.util.Set;

interface Ship {

    Ship grow(Cell cell);

    boolean touches(Ship ship);

    boolean placedOn(Cell cell);

    int size();

    Collection<BoardsQuery.CellView> fullView();

    GameStates.Ship state();

    Ship hit(Cell cell);

    Collection<BoardsQuery.CellView> hitsView();

    boolean isDestroyed();

    Set<Cell> cellsAround();
}
