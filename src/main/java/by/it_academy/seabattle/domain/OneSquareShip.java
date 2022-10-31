package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.SquareQuery;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Set;

final class OneSquareShip extends AbstractShip implements IntactShip {

    private final Square square;

    OneSquareShip(Square square) {
        this.square = square;
    }

    @Override
    public IntactShip grow(Square square) {
        if (this.square.connectedHorizontally(square)) {
            return new HorizontalShip(Set.of(this.square, square));
        }
        if (this.square.connectedVertically(square)) {
            return new VerticalShip(Set.of(this.square, square));
        }
        throw new ShipIsNotValid();
    }

    @Override
    public boolean overlaps(IntactShip ship) {
        return ship.positionedOn(square) ||
                square.neighbours()
                        .stream()
                        .anyMatch(ship::positionedOn);
    }

    @Override
    public boolean positionedOn(Square square) {
        return this.square.equals(square);
    }

    @Override
    public int length() {
        return 1;
    }

    @Override
    public GameStates.Ship state() {
        return new GameStates.Ship(Set.of(square.state()), Set.of());
    }

    @Override
    public Set<Square> neighbourSquares() {
        return square.neighbours();
    }

    @Override
    public Set<GridsQuery.SquareView> view() {
        return Set.of(square.view(GridsQuery.Status.SHIP_SEGMENT));
    }

    @Override
    public SquareQuery.Status square(Square square) {
        if (this.square.equals(square)) {
            return SquareQuery.Status.SHIP_SEGMENT;
        }
        return SquareQuery.Status.UNKNOWN;
    }

    @Override
    Ship hitAfterGuard(Square square) {
        return new SunkenShip(this);
    }
}
