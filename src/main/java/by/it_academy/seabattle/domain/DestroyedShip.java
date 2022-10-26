package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.ShipIsNotIntact;
import by.it_academy.seabattle.usecase.exception.CellHasBeenAlreadyChecked;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

final class DestroyedShip implements Ship {

    private final Set<Cell> cells;

    DestroyedShip(Set<Cell> cells) {
        this.cells = Set.copyOf(cells);
    }

    @Override
    public Ship grow(Cell cell) {
        throw new ShipIsNotIntact();
    }

    @Override
    public boolean touches(Ship ship) {
        return cells.stream()
                .anyMatch(ship::placedOn);
    }

    @Override
    public boolean placedOn(Cell cell) {
        return cells.contains(cell);
    }

    @Override
    public int size() {
        return cells.size();
    }

    @Override
    public Collection<BoardsQuery.CellView> fullView() {
        return cells.stream()
                .map(cell -> cell.view(BoardsQuery.Status.DESTROYED_SHIP_SEGMENT))
                .toList();
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(
                Set.of(),
                cells.stream()
                        .map(Cell::state)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Ship hit(Cell cell) {
        throw new CellHasBeenAlreadyChecked();
    }

    @Override
    public Collection<BoardsQuery.CellView> hitsView() {
        return fullView();
    }

    @Override
    public boolean isDestroyed() {
        return true;
    }

    @Override
    public Set<Cell> cellsAround() {
        return cells.stream()
                .map(Cell::neighboursAndThis)
                .flatMap(Collection::stream)
                .filter(cell -> !cells.contains(cell))
                .collect(Collectors.toSet());
    }
}
