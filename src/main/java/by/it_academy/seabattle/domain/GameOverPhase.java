package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

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
}
