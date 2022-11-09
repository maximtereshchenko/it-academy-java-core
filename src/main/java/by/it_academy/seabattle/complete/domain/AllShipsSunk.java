package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.exception.AllShipsHaveBeenSunk;
import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.util.Set;
import java.util.UUID;

final class AllShipsSunk implements BattleGrid {

    private final SomeShipsFloat original;

    AllShipsSunk(SomeShipsFloat original) {
        this.original = original;
    }

    @Override
    public UUID ownerId() {
        return original.ownerId();
    }

    @Override
    public boolean isOwnedBy(Player player) {
        return original.isOwnedBy(player);
    }

    @Override
    public GameStates.Grid state() {
        return original.state();
    }

    @Override
    public Set<GridsQuery.SquareView> view(Player player) {
        return original.view(player);
    }

    @Override
    public SquareQuery.Status square(Square square) {
        return original.square(square);
    }

    @Override
    public BattleGrid shoot(Square square) {
        throw new AllShipsHaveBeenSunk();
    }

    @Override
    public boolean allShipsSunk() {
        return true;
    }

    @Override
    public boolean hasShipPositionedOn(Square square) {
        return original.hasShipPositionedOn(square);
    }

    @Override
    public RevealedGrid reveal() {
        return original.reveal();
    }
}
