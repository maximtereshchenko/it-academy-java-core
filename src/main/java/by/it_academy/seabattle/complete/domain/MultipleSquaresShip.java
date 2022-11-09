package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;
import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.exception.ShipIsNotValid;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class MultipleSquaresShip extends AbstractShip implements IntactShip {

    private final TreeSet<Square> squares;

    MultipleSquaresShip(Set<Square> squares) {
        this.squares = new TreeSet<>(squares);
    }

    @Override
    public IntactShip grow(Square square) {
        if (squares.contains(square)) {
            throw new ShipIsNotValid();
        }
        return growAfterGuard(square);
    }

    @Override
    public boolean overlaps(IntactShip ship) {
        return Stream.concat(
                        squares.stream(),
                        squares.stream()
                                .map(Square::neighbours)
                                .flatMap(Collection::stream)
                                .distinct()
                )
                .anyMatch(ship::positionedOn);
    }

    @Override
    public boolean positionedOn(Square square) {
        return squares.contains(square);
    }

    @Override
    public int length() {
        return squares.size();
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(
                squares.stream()
                        .map(Square::state)
                        .collect(Collectors.toSet()),
                Set.of()
        );
    }

    @Override
    public Set<Square> neighbourSquares() {
        return squares.stream()
                .map(Square::neighbours)
                .flatMap(Collection::stream)
                .filter(square -> !squares.contains(square))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GridsQuery.SquareView> view() {
        return squares.stream()
                .map(square -> square.view(GridsQuery.Status.SHIP_SEGMENT))
                .collect(Collectors.toSet());
    }

    @Override
    public SquareQuery.Status square(Square square) {
        if (squares.contains(square)) {
            return SquareQuery.Status.SHIP_SEGMENT;
        }
        return SquareQuery.Status.UNKNOWN;
    }

    @Override
    Ship hitAfterGuard(Square square) {
        return new WoundedShip(this, square);
    }

    abstract IntactShip growAfterGuard(Square square);

    TreeSet<Square> squares() {
        return new TreeSet<>(squares);
    }
}
