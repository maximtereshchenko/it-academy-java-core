package by.it_academy.seabattle.usecase;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GridsQuery {

    Grids currentGrids(UUID playerId);

    enum Status {

        SHIP_SEGMENT, DESTROYED_SHIP_SEGMENT, EMPTY
    }

    enum Phase {

        SHIP_POSITIONING, BATTLE, OVER
    }

    record Grids(
            Phase phase,
            UUID turnOwnerId,
            UUID otherPlayerId,
            Set<SquareView> ownedSquares,
            Set<SquareView> opponentSquares
    ) {

        public Optional<UUID> winner() {
            if (phase != Phase.OVER) {
                return Optional.empty();
            }
            return Optional.of(turnOwnerId);
        }
    }

    record SquareView(char column, int row, Status status) {}
}
