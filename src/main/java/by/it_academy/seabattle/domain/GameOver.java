package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.GameIsOver;

import java.util.UUID;

final class GameOver extends AbstractGame {

    private final Board winnerBoard;
    private final Board loserBoard;

    GameOver(UUID id, Board winnerBoard, Board loserBoard) {
        super(id);
        this.winnerBoard = winnerBoard;
        this.loserBoard = loserBoard;
    }

    @Override
    public Game placeShip(Player player, Ship ship) {
        throw new GameIsOver();
    }

    @Override
    public Game shoot(Player player, Cell cell) {
        throw new GameIsOver();
    }

    @Override
    public GameStates.State state() {
        return state(winnerBoard, loserBoard, GameStates.Phase.OVER);
    }

    @Override
    public BoardsQuery.Boards view(Player player) {
        return new BoardsQuery.Boards(
                BoardsQuery.Phase.OVER,
                winnerBoard.ownerId(),
                loserBoard.ownerId(),
                boardByCondition(winnerBoard, loserBoard, boardOwnerPredicate(player)).fullView(),
                boardByCondition(winnerBoard, loserBoard, boardOwnerPredicate(player).negate()).fullView()
        );
    }
}
