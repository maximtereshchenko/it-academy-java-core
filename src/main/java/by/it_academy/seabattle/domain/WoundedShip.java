package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.CellIsNotRelatedToShip;
import by.it_academy.seabattle.usecase.exception.ShipIsNotIntact;
import by.it_academy.seabattle.usecase.exception.CellHasBeenAlreadyChecked;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class WoundedShip implements Ship {

    private final Set<Cell> intactCells;
    private final Set<Cell> destroyedCells;

    WoundedShip(Set<Cell> intactCells, Set<Cell> destroyedCells) {
        this.intactCells = intactCells;
        this.destroyedCells = Set.copyOf(destroyedCells);
    }

    static Ship of(Set<Cell> shipCells, Cell hit) {
        if (!shipCells.contains(hit)) {
            throw new CellIsNotRelatedToShip();
        }
        HashSet<Cell> copy = new HashSet<>(shipCells);
        copy.remove(hit);
        return new WoundedShip(copy, Set.of(hit));
    }

    @Override
    public Ship grow(Cell cell) {
        throw new ShipIsNotIntact();
    }

    @Override
    public boolean touches(Ship ship) {
        return false; //TODO
    }

    @Override
    public boolean placedOn(Cell cell) {
        return intactCells.contains(cell) || destroyedCells.contains(cell);
    }

    @Override
    public int size() {
        return intactCells.size() + destroyedCells.size();
    }

    @Override
    public Collection<BoardsQuery.CellView> fullView() {
        return Stream.concat(
                        intactCells.stream()
                                .map(cell -> cell.view(BoardsQuery.Status.SHIP_SEGMENT)),
                        destroyedCells.stream()
                                .map(cell -> cell.view(BoardsQuery.Status.DESTROYED_SHIP_SEGMENT))
                )
                .toList();
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(
                intactCells.stream()
                        .map(Cell::state)
                        .collect(Collectors.toSet()),
                destroyedCells.stream()
                        .map(Cell::state)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Ship hit(Cell cell) {
        if (!intactCells.contains(cell)) {
            throw new CellHasBeenAlreadyChecked();
        }
        if (intactCells.size() == 1) {
            Set<Cell> copy = new HashSet<>(destroyedCells);
            copy.addAll(intactCells);
            return new DestroyedShip(copy);
        }
        Set<Cell> intactCellsCopy = new HashSet<>(intactCells);
        intactCellsCopy.remove(cell);
        Set<Cell> destroyedCellsCopy = new HashSet<>(destroyedCells);
        destroyedCellsCopy.add(cell);
        return new WoundedShip(intactCellsCopy, destroyedCellsCopy);
    }

    @Override
    public Collection<BoardsQuery.CellView> hitsView() {
        return destroyedCells.stream()
                .map(cell -> cell.view(BoardsQuery.Status.DESTROYED_SHIP_SEGMENT))
                .toList();
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public Set<Cell> cellsAround() {
        return Stream.concat(
                        intactCells.stream(),
                        destroyedCells.stream()
                )
                .map(Cell::neighboursAndThis)
                .flatMap(Collection::stream)
                .filter(cell -> !intactCells.contains(cell))
                .filter(cell -> !destroyedCells.contains(cell))
                .collect(Collectors.toSet());
    }
}
