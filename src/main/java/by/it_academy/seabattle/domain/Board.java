package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.CellHasBeenAlreadyChecked;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Board {

    private final UUID ownerId;
    private final Set<Ship> ships;
    private final Set<Cell> checkedEmptyCells;

    Board(UUID ownerId, Set<Ship> ships, Set<Cell> checkedEmptyCells) {
        this.ownerId = ownerId;
        this.ships = Set.copyOf(ships);
        this.checkedEmptyCells = Set.copyOf(checkedEmptyCells);
    }

    Board(Player player) {
        this(player.id(), Set.of(), Set.of());
    }

    UUID ownerId() {
        return ownerId;
    }

    Board place(Ship ship) {
        HashSet<Ship> copy = new HashSet<>(ships);
        copy.add(ship);
        return new Board(ownerId, copy, checkedEmptyCells);
    }

    boolean canNotBePlaced(Ship ship) {
        return touchesOtherShips(ship) || shipLimitReached(ship);
    }

    boolean isFull() {
        return ships.stream()
                .collect(Collectors.groupingBy(Ship::size, Collectors.counting()))
                .equals(Map.of(1, 4L, 2, 3L, 3, 2L, 4, 1L));
    }

    GameStates.Board state() {
        return new GameStates.Board(
                ships.stream()
                        .map(Ship::state)
                        .collect(Collectors.toSet()),
                checkedEmptyCells.stream()
                        .map(Cell::state)
                        .collect(Collectors.toSet())
        );
    }

    Set<BoardsQuery.CellView> view(Player player) {
        return cellViews(shipViewFunction(player));
    }

    Set<BoardsQuery.CellView> fullView() {
        return cellViews(Ship::fullView);
    }

    private Set<BoardsQuery.CellView> cellViews(Function<Ship, Collection<BoardsQuery.CellView>> player) {
        return Stream.concat(
                        ships.stream()
                                .map(player)
                                .flatMap(Collection::stream),
                        checkedEmptyCells.stream()
                                .map(cell ->
                                        new BoardsQuery.CellView(cell.row(), cell.column(), BoardsQuery.Status.EMPTY)
                                )
                )
                .collect(Collectors.toSet());
    }

    private boolean shipLimitReached(Ship ship) {
        return ships.stream()
                .filter(placedShip -> placedShip.size() == ship.size())
                .count() == 5 - ship.size();
    }

    Board shoot(Cell cell) {
        if (checkedEmptyCells.contains(cell)) {
            throw new CellHasBeenAlreadyChecked();
        }
        return ships.stream()
                .filter(ship -> ship.placedOn(cell))
                .findAny()
                .map(ship -> withHitShip(ship, cell))
                .orElseGet(() -> new Board(ownerId, ships, withCheckedCell(cell)));
    }

    private boolean touchesOtherShips(Ship ship) {
        return ships.stream()
                .anyMatch(placedShip -> placedShip.touches(ship));
    }

    private Function<Ship, Collection<BoardsQuery.CellView>> shipViewFunction(Player player) {
        if (ownerId.equals(player.id())) {
            return Ship::fullView;
        }
        return Ship::hitsView;
    }

    boolean shipTargeted(Cell cell) {
        return ships.stream()
                .anyMatch(ship -> ship.placedOn(cell));
    }

    boolean allShipsDestroyed() {
        return ships.stream()
                .allMatch(Ship::isDestroyed);
    }

    private Set<Cell> withCheckedCell(Cell cell) {
        HashSet<Cell> copy = new HashSet<>(checkedEmptyCells);
        copy.add(cell);
        return copy;
    }

    private Board withHitShip(Ship ship, Cell cell) {
        Ship hitShip = ship.hit(cell);
        HashSet<Ship> copy = new HashSet<>(ships);
        copy.remove(ship);
        copy.add(hitShip);
        return new Board(ownerId, copy, checkedEmptyCells(hitShip));
    }

    private Set<Cell> checkedEmptyCells(Ship ship) {
        if (ship.isDestroyed()) {
            Set<Cell> copy = new HashSet<>(checkedEmptyCells);
            copy.addAll(ship.cellsAround());
            return copy;
        }
        return checkedEmptyCells;
    }
}
