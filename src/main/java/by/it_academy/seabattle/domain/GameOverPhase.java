package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

import java.util.UUID;

final class GameOverPhase extends AbstractGame {

    private final UUID id;
    private final RevealedGrid winnerGrid;
    private final RevealedGrid loserGrid;

    GameOverPhase(UUID id, RevealedGrid winnerGrid, RevealedGrid loserGrid) {
        this.id = id;
        this.winnerGrid = winnerGrid;
        this.loserGrid = loserGrid;
    }


    @Override
    public GameStates.State state() {
        return new GameStates.State(id, GameStates.Phase.OVER, winnerGrid.state(), loserGrid.state());
    }

    @Override
    public GridsQuery.Grids view(Player player) {
        return new GridsQuery.Grids(
                GridsQuery.Phase.OVER,
                winnerGrid.ownerId(),
                loserGrid.ownerId(),
                ownedGrid(player, winnerGrid, loserGrid).view(player),
                otherPlayerGrid(player, winnerGrid, loserGrid).view(player)
        );
    }
}
