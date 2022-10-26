package by.it_academy.seabattle.port;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GameStates {

    Optional<State> findByPlayerId(UUID playerId);

    void save(State state);

    enum Phase {

        SHIP_PLACEMENT, BATTLE, OVER
    }

    record State(
            UUID id,
            Phase phase,
            UUID nextTurnOwnerId,
            UUID otherPlayerId,
            Board nextTurnOwnerBoard,
            Board otherPlayerBoard
    ) {
    }

    record Board(Set<Ship> ships, Set<Cell> otherCells) {
    }

    record Ship(Set<Cell> intactCells, Set<Cell> destroyedCells) {
    }

    record Cell(char row, int column) {
    }
}
