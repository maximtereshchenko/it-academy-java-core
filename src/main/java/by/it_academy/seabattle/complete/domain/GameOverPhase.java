package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.time.Instant;
import java.util.UUID;

final class GameOverPhase extends AbstractGame {

    private final UUID id;
    private final RevealedGrid winnerGrid;
    private final RevealedGrid loserGrid;
    private final Instant startedAt;

    GameOverPhase(UUID id, RevealedGrid winnerGrid, RevealedGrid loserGrid, Instant startedAt) {
        this.id = id;
        this.winnerGrid = winnerGrid;
        this.loserGrid = loserGrid;
        this.startedAt = startedAt;
    }


    @Override
    public UUID firstPlayerId() {
        return winnerGrid.ownerId();
    }

    @Override
    public UUID secondPlayerId() {
        return loserGrid.ownerId();
    }

    @Override
    public GameStates.State state() {
        return new GameStates.State(id, GameStates.Phase.OVER, winnerGrid.state(), loserGrid.state(), startedAt);
    }

    @Override
    public GridsQuery.Grids view(Player player) {
        return new GridsQuery.Grids(
                GridsQuery.Phase.OVER,
                winnerGrid.ownerId(),
                loserGrid.ownerId(),
                ownedGrid(player, winnerGrid, loserGrid).view(player),
                otherPlayerGrid(player, winnerGrid, loserGrid).view(player),
                startedAt
        );
    }

    @Override
    public boolean isNotOver() {
        return false;
    }

    @Override
    public boolean hasNotAllShips() {
        return false;
    }

    @Override
    public SquareQuery.Status square(Player player, Square square) {
        return otherPlayerGrid(player, winnerGrid, loserGrid).square(square);
    }
}
