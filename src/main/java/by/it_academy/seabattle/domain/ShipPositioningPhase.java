package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.GridIsComplete;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.UUID;

final class ShipPositioningPhase extends AbstractGame {

    private final UUID id;
    private final ShipPositioningGrid firstPlayerGrid;
    private final ShipPositioningGrid secondPlayerGrid;

    ShipPositioningPhase(UUID id, ShipPositioningGrid firstPlayerGrid, ShipPositioningGrid secondPlayerGrid) {
        this.id = id;
        this.firstPlayerGrid = firstPlayerGrid;
        this.secondPlayerGrid = secondPlayerGrid;
    }

    ShipPositioningPhase(Player firstPlayer, Player secondPlayer) {
        this(UUID.randomUUID(), new IncompleteGrid(firstPlayer), new IncompleteGrid(secondPlayer));
    }

    @Override
    public GameStates.State state() {
        return new GameStates.State(
                id,
                GameStates.Phase.SHIP_POSITIONING,
                firstPlayerGrid.state(),
                secondPlayerGrid.state()
        );
    }

    @Override
    public GridsQuery.Grids view(Player player) {
        return new GridsQuery.Grids(
                GridsQuery.Phase.SHIP_POSITIONING,
                firstPlayerGrid.ownerId(),
                secondPlayerGrid.ownerId(),
                ownedGrid(player, firstPlayerGrid, secondPlayerGrid).view(player),
                otherPlayerGrid(player, firstPlayerGrid, secondPlayerGrid).view(player)
        );
    }

    Game position(Player player, IntactShip ship) {
        if (canNotBePositioned(player, ship)) {
            throw new ShipIsNotValid();
        }
        ShipPositioningPhase game = withShip(player, ship);
        if (game.hasCompleteGrids()) {
            return game.battlePhase();
        }
        return game;
    }

    Game fill(Player player, ShipFactory shipFactory) {
        if (ownedGrid(player, firstPlayerGrid, secondPlayerGrid).isComplete()) {
            throw new GridIsComplete();
        }
        ShipPositioningPhase game = this;
        while (game.hasNotCompleteGrid(player)) {
            IntactShip ship = shipFactory.random();
            if (game.canNotBePositioned(player, ship)) {
                continue;
            }
            game = game.withShip(player, ship);
        }
        if (game.hasCompleteGrids()) {
            return game.battlePhase();
        }
        return game;
    }

    private boolean hasNotCompleteGrid(Player player) {
        return !ownedGrid(player, firstPlayerGrid, secondPlayerGrid).isComplete();
    }

    private Game battlePhase() {
        return new BattlePhase(id, firstPlayerGrid.battleGrid(), secondPlayerGrid.battleGrid());
    }

    private boolean canNotBePositioned(Player player, IntactShip ship) {
        return ownedGrid(player, firstPlayerGrid, secondPlayerGrid).canNotBePositioned(ship);
    }

    private ShipPositioningPhase withShip(Player player, IntactShip ship) {
        return new ShipPositioningPhase(
                id,
                gridWithShipIfOwned(firstPlayerGrid, player, ship),
                gridWithShipIfOwned(secondPlayerGrid, player, ship)
        );
    }

    private boolean hasCompleteGrids() {
        return firstPlayerGrid.isComplete() && secondPlayerGrid.isComplete();
    }

    private ShipPositioningGrid gridWithShipIfOwned(ShipPositioningGrid grid, Player player, IntactShip ship) {
        if (grid.isOwnedBy(player)) {
            return grid.position(ship);
        }
        return grid;
    }
}
