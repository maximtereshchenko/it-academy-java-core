package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.AllShipsHaveBeenSunk;

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
