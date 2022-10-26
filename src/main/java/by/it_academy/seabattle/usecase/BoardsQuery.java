package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BoardsQuery {

    Boards getCurrentBoards(UUID playerId) throws PlayerWasNotFound, GameWasNotFound;

    enum Status {

        SHIP_SEGMENT, DESTROYED_SHIP_SEGMENT, EMPTY
    }

    enum Phase {

        SHIP_PLACEMENT, BATTLE, OVER
    }

    record Boards(
            Phase phase,
            UUID turnOwnerId,
            UUID otherPlayerId,
            Set<CellView> ownedCells,
            Set<CellView> opponentCells
    ) {

        Optional<UUID> winner() {
            if (phase != Phase.OVER) {
                return Optional.empty();
            }
            return Optional.of(turnOwnerId);
        }
    }

    record CellView(char row, int column, Status status) {
    }
}
