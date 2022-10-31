package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;

import java.time.Instant;
import java.util.UUID;

final class BattlePhase extends AbstractGame {

    private final UUID id;
    private final BattleGrid turnOwnerGrid;
    private final BattleGrid otherPlayerGrid;
    private final Instant startedAt;

    BattlePhase(UUID id, BattleGrid turnOwnerGrid, BattleGrid otherPlayerGrid, Instant startedAt) {
        this.id = id;
        this.turnOwnerGrid = turnOwnerGrid;
        this.otherPlayerGrid = otherPlayerGrid;
        this.startedAt = startedAt;
    }

    @Override
    public UUID firstPlayerId() {
        return turnOwnerGrid.ownerId();
    }

    @Override
    public UUID secondPlayerId() {
        return otherPlayerGrid.ownerId();
    }

    @Override
    public GameStates.State state() {
        return new GameStates.State(
                id,
                GameStates.Phase.BATTLE,
                turnOwnerGrid.state(),
                otherPlayerGrid.state(),
                startedAt
        );
    }

    @Override
    public GridsQuery.Grids view(Player player) {
        return new GridsQuery.Grids(
                GridsQuery.Phase.BATTLE,
                turnOwnerGrid.ownerId(),
                otherPlayerGrid.ownerId(),
                ownedGrid(player, turnOwnerGrid, otherPlayerGrid).view(player),
                otherPlayerGrid(player, turnOwnerGrid, otherPlayerGrid).view(player),
                startedAt
        );
    }

    @Override
    public boolean isNotOver() {
        return true;
    }

    @Override
    public boolean hasNotAllShips() {
        return false;
    }

    Game shoot(Player player, Square square) {
        if (!turnOwnerGrid.isOwnedBy(player)) {
            throw new NotYourTurn();
        }
        BattleGrid grid = otherPlayerGrid.shoot(square);
        if (grid.hasShipPositionedOn(square)) {
            if (grid.allShipsSunk()) {
                return new GameOverPhase(id, turnOwnerGrid.reveal(), grid.reveal(), startedAt);
            }
            return new BattlePhase(id, turnOwnerGrid, grid, startedAt);
        }
        return new BattlePhase(id, grid, turnOwnerGrid, startedAt);
    }
}
