package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;

import java.util.UUID;

final class BattlePhase extends AbstractGame {

    private final UUID id;
    private final BattleGrid turnOwnerGrid;
    private final BattleGrid otherPlayerGrid;

    BattlePhase(UUID id, BattleGrid turnOwnerGrid, BattleGrid otherPlayerGrid) {
        this.id = id;
        this.turnOwnerGrid = turnOwnerGrid;
        this.otherPlayerGrid = otherPlayerGrid;
    }

    @Override
    public GameStates.State state() {
        return new GameStates.State(id, GameStates.Phase.BATTLE, turnOwnerGrid.state(), otherPlayerGrid.state());
    }

    @Override
    public GridsQuery.Grids view(Player player) {
        return new GridsQuery.Grids(
                GridsQuery.Phase.BATTLE,
                turnOwnerGrid.ownerId(),
                otherPlayerGrid.ownerId(),
                ownedGrid(player, turnOwnerGrid, otherPlayerGrid).view(player),
                otherPlayerGrid(player, turnOwnerGrid, otherPlayerGrid).view(player)
        );
    }

    Game shoot(Player player, Square square) {
        if (!turnOwnerGrid.isOwnedBy(player)) {
            throw new NotYourTurn();
        }
        BattleGrid grid = otherPlayerGrid.shoot(square);
        if (grid.hasShipPositionedOn(square)) {
            if (grid.allShipsSunk()) {
                return new GameOverPhase(id, turnOwnerGrid.reveal(), grid.reveal());
            }
            return new BattlePhase(id, turnOwnerGrid, grid);
        }
        return new BattlePhase(id, grid, turnOwnerGrid);
    }
}
