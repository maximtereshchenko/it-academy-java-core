package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.SquareQuery;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class WoundedShip extends AbstractShip {

    private final Ship original;
    private final Set<Square> hits;

    WoundedShip(Ship original, Square square) {
        this(original, Set.of(square));
    }

    private WoundedShip(Ship original, Set<Square> hits) {
        this.original = original;
        this.hits = Set.copyOf(hits);
    }

    @Override
    public boolean positionedOn(Square square) {
        return original.positionedOn(square);
    }

    @Override
    public int length() {
        return original.length();
    }

    @Override
    public GameStates.Ship state() {
        Set<GameStates.Square> destroyedSquares = hits.stream()
                .map(Square::state)
                .collect(Collectors.toSet());
        return new GameStates.Ship(
                original.state()
                        .intactSquares()
                        .stream()
                        .filter(square -> !destroyedSquares.contains(square))
                        .collect(Collectors.toSet()),
                destroyedSquares
        );
    }

    @Override
    public Set<Square> neighbourSquares() {
        return original.neighbourSquares();
    }

    @Override
    public Set<GridsQuery.SquareView> view() {
        return Stream.concat(
                        original.view()
                                .stream()
                                .filter(this::isHit),
                        hits.stream()
                                .map(square -> square.view(GridsQuery.Status.DESTROYED_SHIP_SEGMENT))
                )
                .collect(Collectors.toSet());
    }

    @Override
    public SquareQuery.Status square(Square square) {
        if (hits.contains(square)) {
            return SquareQuery.Status.DESTROYED_SHIP_SEGMENT;
        }
        return original.square(square);
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    Ship hitAfterGuard(Square square) {
        if (hits.contains(square)) {
            throw new SquareIsNotValid();
        }
        Set<Square> copy = new HashSet<>(hits);
        copy.add(square);
        WoundedShip ship = new WoundedShip(original, copy);
        if (ship.allSegmentsHit()) {
            return new SunkenShip(ship);
        }
        return ship;
    }

    private boolean isHit(GridsQuery.SquareView squareView) {
        return hits.contains(Square.of(squareView.column(), squareView.row()));
    }

    private boolean allSegmentsHit() {
        return hits.size() == original.length();
    }
}
