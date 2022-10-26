package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

class ShipFactory {

    Ship ship(Collection<String> coordinates) {
        Set<Cell> cells = coordinates.stream()
                .map(Cell::of)
                .collect(Collectors.toSet());
        if (cells.size() != coordinates.size()) {
            throw new ShipIsNotValid();
        }
        return ship(cells, Set.of());
    }

    Set<Ship> ships(Collection<GameStates.Ship> ships) {
        return ships.stream()
                .map(ship -> ship(cells(ship.intactCells()), cells(ship.destroyedCells())))
                .collect(Collectors.toSet());
    }

    private Set<Cell> cells(Set<GameStates.Cell> cells) {
        return cells.stream()
                .map(cell -> new Cell(cell.row(), cell.column()))
                .collect(Collectors.toSet());
    }

    private Ship ship(Set<Cell> intactCells, Set<Cell> destroyedCells) {
        if (intactCells.isEmpty() && destroyedCells.isEmpty()) {
            throw new ShipIsNotValid();
        }
        if (intactCells.isEmpty()) {
            return new DestroyedShip(destroyedCells);
        }
        if (destroyedCells.isEmpty()) {
            Iterator<Cell> cellIterator = intactCells.iterator();
            Ship ship = new OneCellShip(cellIterator.next());
            while (cellIterator.hasNext()) {
                ship = ship.grow(cellIterator.next());
            }
            return ship;
        }
        return new WoundedShip(intactCells, destroyedCells);
    }
}
