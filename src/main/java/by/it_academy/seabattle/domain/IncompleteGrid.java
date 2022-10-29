package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;
import by.it_academy.seabattle.usecase.exception.UnexpectedException;

import java.util.*;
import java.util.stream.Collectors;

final class IncompleteGrid extends AbstractGrid implements ShipPositioningGrid {

    private final Set<IntactShip> ships;

    IncompleteGrid(UUID ownerId) {
        this(ownerId, Set.of());
    }

    IncompleteGrid(Player player) {
        this(player.id());
    }

    private IncompleteGrid(UUID ownerId, Set<IntactShip> ships) {
        super(ownerId);
        this.ships = Set.copyOf(ships);
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public ShipPositioningGrid position(IntactShip ship) {
        if (canNotBePositioned(ship)) {
            throw new ShipIsNotValid();
        }
        Set<IntactShip> copy = new HashSet<>(ships);
        copy.add(ship);
        IncompleteGrid grid = new IncompleteGrid(ownerId(), copy);
        if (grid.hasAllShips()) {
            return new CompleteGrid(grid);
        }
        return grid;
    }

    @Override
    public boolean canNotBePositioned(IntactShip ship) {
        return overlaps(ship) || limitReached(ship);
    }

    @Override
    public BattleGrid battleGrid() {
        throw new UnexpectedException("Grid is not complete");
    }

    @Override
    public GameStates.Grid state() {
        return state(ships);
    }

    @Override
    public Set<GridsQuery.SquareView> view(Player player) {
        if (!isOwnedBy(player)) {
            return Set.of();
        }
        return ships.stream()
                .map(Ship::view)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    Set<IntactShip> ships() {
        return ships;
    }

    private boolean limitReached(IntactShip ship) {
        return ships.stream()
                .filter(positionedShip -> positionedShip.length() == ship.length())
                .count() == 5 - ship.length();
    }

    private boolean overlaps(IntactShip ship) {
        return ships.stream()
                .anyMatch(positionedShip -> positionedShip.overlaps(ship));
    }

    private boolean hasAllShips() {
        return ships.stream()
                .collect(Collectors.groupingBy(IntactShip::length, Collectors.counting()))
                .equals(Map.of(1, 4L, 2, 3L, 3, 2L, 4, 1L));
    }
}
