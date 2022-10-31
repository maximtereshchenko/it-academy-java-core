package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.SquareQuery;
import by.it_academy.seabattle.usecase.exception.ShipWasSunk;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class SunkenShip implements Ship {

    private final Ship original;

    SunkenShip(Ship original) {
        this.original = original;
    }

    @Override
    public Ship hit(Square square) {
        throw new ShipWasSunk();
    }

    @Override
    public boolean positionedOn(Square square) {
        return original.positionedOn(square);
    }

    @Override
    public boolean isSunk() {
        return true;
    }

    @Override
    public int length() {
        return original.length();
    }

    @Override
    public GameStates.Ship state() {
        GameStates.Ship state = this.original.state();
        return new GameStates.Ship(
                Set.of(),
                Stream.of(
                                state.intactSquares(),
                                state.destroyedSquares()
                        )
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public Set<Square> neighbourSquares() {
        return original.neighbourSquares();
    }

    @Override
    public Set<GridsQuery.SquareView> view() {
        return original.view()
                .stream()
                .map(squareView ->
                        new GridsQuery.SquareView(
                                squareView.column(),
                                squareView.row(),
                                GridsQuery.Status.DESTROYED_SHIP_SEGMENT
                        )
                )
                .collect(Collectors.toSet());
    }

    @Override
    public SquareQuery.Status square(Square square) {
        SquareQuery.Status status = original.square(square);
        if (status == SquareQuery.Status.SHIP_SEGMENT) {
            return SquareQuery.Status.DESTROYED_SHIP_SEGMENT;
        }
        return status;
    }
}
