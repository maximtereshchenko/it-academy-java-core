package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.SquareQuery;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class SomeShipsFloat extends AbstractGrid implements BattleGrid {

    private final Set<Ship> ships;
    private final Set<Square> checked;

    SomeShipsFloat(UUID ownerId, Set<? extends Ship> ships) {
        this(ownerId, ships, Set.of());
    }

    SomeShipsFloat(UUID ownerId, Set<? extends Ship> ships, Set<Square> checked) {
        super(ownerId);
        this.ships = Set.copyOf(ships);
        this.checked = Set.copyOf(checked);
    }

    @Override
    public BattleGrid shoot(Square square) {
        return shipBySquare(square)
                .map(ship -> withShipHit(ship, square))
                .orElseGet(() -> withCheckedSquare(square));
    }

    @Override
    public boolean allShipsSunk() {
        return false;
    }

    @Override
    public boolean hasShipPositionedOn(Square square) {
        return shipBySquare(square).isPresent();
    }

    @Override
    public RevealedGrid reveal() {
        return new RevealedGrid(ownerId(), ships, checked);
    }

    @Override
    public GameStates.Grid state() {
        return state(ships, checked);
    }

    @Override
    public Set<GridsQuery.SquareView> view(Player player) {
        return Stream.concat(
                        ships.stream()
                                .map(Ship::view)
                                .flatMap(Collection::stream),
                        checked.stream()
                                .map(square -> square.view(GridsQuery.Status.EMPTY))
                )
                .filter(squareView -> isOwnedBy(player) || squareView.status() != GridsQuery.Status.SHIP_SEGMENT)
                .collect(Collectors.toSet());
    }

    @Override
    public SquareQuery.Status square(Square square) {
        SquareQuery.Status status = square(ships, checked, square);
        if (status == SquareQuery.Status.SHIP_SEGMENT || status == SquareQuery.Status.EMPTY) {
            return SquareQuery.Status.UNKNOWN;
        }
        return status;
    }

    private BattleGrid withCheckedSquare(Square square) {
        if (checked.contains(square)) {
            throw new SquareIsNotValid();
        }
        Set<Square> copy = new HashSet<>(checked);
        copy.add(square);
        return new SomeShipsFloat(ownerId(), ships, copy);
    }

    private BattleGrid withShipHit(Ship ship, Square square) {
        Ship targetedShip = ship.hit(square);
        Set<Ship> copy = new HashSet<>(ships);
        copy.remove(ship);
        copy.add(targetedShip);
        SomeShipsFloat grid = new SomeShipsFloat(ownerId(), copy, checked(targetedShip));
        if (grid.hasNoFloatingShips()) {
            return new AllShipsSunk(grid);
        }
        return grid;
    }

    private Set<Square> checked(Ship ship) {
        if (!ship.isSunk()) {
            return checked;
        }
        Set<Square> copy = new HashSet<>(checked);
        copy.addAll(ship.neighbourSquares());
        return copy;
    }

    private boolean hasNoFloatingShips() {
        return ships.stream()
                .allMatch(Ship::isSunk);
    }

    private Optional<Ship> shipBySquare(Square square) {
        return ships.stream()
                .filter(ship -> ship.positionedOn(square))
                .findAny();
    }
}
