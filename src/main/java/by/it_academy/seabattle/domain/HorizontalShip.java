package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

final class HorizontalShip extends MultipleSegmentsShip {

    private HorizontalShip(Set<Cell> segments) {
        super(segments);
    }

    static Ship of(Cell cell1, Cell cell2) {
        if (!(Cell.Relation.RIGHT.between(cell1, cell2) || Cell.Relation.LEFT.between(cell1, cell2))) {
            throw new ShipIsNotValid();
        }
        return new HorizontalShip(Set.of(cell1, cell2));
    }

    @Override
    public Ship grow(Cell cell) {
        ensureSizeIsNotMaximum();
        if (!(rightRelated(cell) || leftRelated(cell))) {
            throw new ShipIsNotValid();
        }
        Set<Cell> copy = new HashSet<>(cells());
        copy.add(cell);
        return new HorizontalShip(copy);
    }

    private boolean rightRelated(Cell cell) {
        return isRelated(cell, Comparator.comparingInt(Cell::column), Cell.Relation.RIGHT);
    }

    private boolean leftRelated(Cell cell) {
        return isRelated(cell, Comparator.comparingInt(Cell::column).reversed(), Cell.Relation.LEFT);
    }
}
