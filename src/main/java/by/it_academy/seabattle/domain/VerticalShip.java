package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

final class VerticalShip extends MultipleSegmentsShip {

    private VerticalShip(Set<Cell> segments) {
        super(segments);
    }

    static Ship of(Cell cell1, Cell cell2) {
        if (!(Cell.Relation.UP.between(cell1, cell2) || Cell.Relation.DOWN.between(cell1, cell2))) {
            throw new ShipIsNotValid();
        }
        return new VerticalShip(Set.of(cell1, cell2));
    }

    @Override
    public Ship grow(Cell cell) {
        if (!(upRelated(cell) || downRelated(cell))) {
            throw new ShipIsNotValid();
        }
        Set<Cell> copy = new HashSet<>(cells());
        copy.add(cell);
        return new VerticalShip(copy);
    }

    private boolean upRelated(Cell cell) {
        return isRelated(cell, Comparator.comparingInt(Cell::row).reversed(), Cell.Relation.UP);
    }

    private boolean downRelated(Cell cell) {
        return isRelated(cell, Comparator.comparingInt(Cell::row), Cell.Relation.DOWN);
    }
}
