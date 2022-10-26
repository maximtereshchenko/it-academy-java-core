package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

abstract class MultipleSegmentsShip implements Ship {

    private final Set<Cell> cells;

    MultipleSegmentsShip(Set<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(
                cells.stream()
                        .map(Cell::state)
                        .collect(Collectors.toSet()),
                Set.of()
        );
    }

    @Override
    public Collection<BoardsQuery.CellView> fullView() {
        return cells.stream()
                .map(cell -> cell.view(BoardsQuery.Status.SHIP_SEGMENT))
                .toList();
    }

    @Override
    public boolean touches(Ship ship) {
        return cells.stream()
                .map(Cell::neighboursAndThis)
                .flatMap(Collection::stream)
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
    public Ship hit(Cell cell) {
        return WoundedShip.of(cells, cell);
    }

    @Override
    public Collection<BoardsQuery.CellView> hitsView() {
        return List.of();
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public Set<Cell> cellsAround() {
        return cells.stream()
                .map(Cell::neighboursAndThis)
                .flatMap(Collection::stream)
                .filter(cell -> !cells.contains(cell))
                .collect(Collectors.toSet());
    }

    Set<Cell> cells() {
        return Set.copyOf(cells);
    }

    boolean isRelated(Cell cell, Comparator<Cell> comparator, Cell.Relation target) {
        return cells.stream()
                .max(comparator)
                .flatMap(leftMost -> leftMost.relation(cell))
                .map(relation -> relation == target)
                .orElse(Boolean.FALSE);
    }

    void ensureSizeIsNotMaximum() {
        if (cells.size() == 4) {
            throw new ShipIsNotValid();
        }
    }
}
