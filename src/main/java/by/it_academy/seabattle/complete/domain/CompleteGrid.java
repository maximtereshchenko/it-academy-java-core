package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;
import by.it_academy.seabattle.complete.usecase.exception.ShipIsNotValid;

import java.util.Set;
import java.util.UUID;

final class CompleteGrid implements ShipPositioningGrid {

    private final IncompleteGrid original;

    CompleteGrid(IncompleteGrid original) {
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
    public boolean isComplete() {
        return true;
    }

    @Override
    public ShipPositioningGrid position(IntactShip ship) {
        throw new ShipIsNotValid();
    }

    @Override
    public boolean canNotBePositioned(IntactShip ship) {
        return true;
    }

    @Override
    public BattleGrid battleGrid() {
        return new SomeShipsFloat(original.ownerId(), original.ships());
    }
}
