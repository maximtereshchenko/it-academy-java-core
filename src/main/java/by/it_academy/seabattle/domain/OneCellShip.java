package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class OneCellShip implements Ship {

    private final Cell cell;

    OneCellShip(Cell cell) {
        this.cell = cell;
    }

    @Override
    public Ship grow(Cell cell) {
        return this.cell.relation(cell)
                .map(relation -> relativeShip(relation, cell))
                .orElseThrow(() -> new ShipIsNotValid("Could not grow %s from %s".formatted(cell, this.cell)));
    }

    @Override
    public boolean touches(Ship ship) {
        return cell.neighboursAndThis()
                .stream()
                .anyMatch(ship::placedOn);
    }

    @Override
    public boolean placedOn(Cell cell) {
        return this.cell.equals(cell);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Collection<BoardsQuery.CellView> fullView() {
        return List.of(cell.view(BoardsQuery.Status.SHIP_SEGMENT));
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(Set.of(cell.state()), Set.of());
    }

    @Override
    public Ship hit(Cell cell) {
        return new DestroyedShip(Set.of(this.cell));
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
        Set<Cell> copy = new HashSet<>(cell.neighboursAndThis());
        copy.remove(cell);
        return copy;
    }

    private Ship relativeShip(Cell.Relation relation, Cell cell) {
        if (relation == Cell.Relation.LEFT || relation == Cell.Relation.RIGHT) {
            return HorizontalShip.of(this.cell, cell);
        }
        return VerticalShip.of(this.cell, cell);
    }
}
