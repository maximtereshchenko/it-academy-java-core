package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

abstract class AbstractGame implements Game {

    private final UUID id;

    AbstractGame(UUID id) {
        this.id = id;
    }

    @Override
    public UUID id() {
        return id;
    }

    GameStates.State state(Board firstBoard, Board secondBoard, GameStates.Phase phase) {
        return new GameStates.State(
                id,
                phase,
                firstBoard.ownerId(),
                secondBoard.ownerId(),
                firstBoard.state(),
                secondBoard.state()
        );
    }

    BoardsQuery.Boards view(Player player, Board firstBoard, Board secondBoard, BoardsQuery.Phase phase) {
        return new BoardsQuery.Boards(
                phase,
                firstBoard.ownerId(),
                secondBoard.ownerId(),
                boardByCondition(firstBoard, secondBoard, boardOwnerPredicate(player)).view(player),
                boardByCondition(firstBoard, secondBoard, boardOwnerPredicate(player).negate()).view(player)
        );
    }

    Board boardByCondition(Board firstBoard, Board secondBoard, Predicate<UUID> predicate) {
        if (predicate.test(firstBoard.ownerId())) {
            return firstBoard;
        }
        return secondBoard;
    }

    Predicate<UUID> boardOwnerPredicate(Player player) {
        return ownerId -> player.id().equals(ownerId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        var that = (AbstractGame) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
