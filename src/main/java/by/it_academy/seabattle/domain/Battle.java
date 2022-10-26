package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.BattleHasBeenStarted;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;

import java.util.UUID;

class Battle extends AbstractGame {

    private final Board turnOwnerBoard;
    private final Board otherPlayerBoard;

    Battle(UUID id, Board turnOwnerBoard, Board otherPlayerBoard) {
        super(id);
        this.turnOwnerBoard = turnOwnerBoard;
        this.otherPlayerBoard = otherPlayerBoard;
    }

    @Override
    public Game placeShip(Player player, Ship ship) {
        throw new BattleHasBeenStarted();
    }

    @Override
    public Game shoot(Player player, Cell cell) {
        if (!turnOwnerBoard.ownerId().equals(player.id())) {
            throw new NotYourTurn();
        }
        if (turnOwnerBoard.shipTargeted(cell)) {
            Board board = otherPlayerBoard.shoot(cell);
            if (board.allShipsDestroyed()) {
                return new GameOver(id(), turnOwnerBoard, board);
            }
            return new Battle(id(), turnOwnerBoard, board);
        }
        return new Battle(id(), otherPlayerBoard.shoot(cell), turnOwnerBoard);
    }

    @Override
    public GameStates.State state() {
        return state(turnOwnerBoard, otherPlayerBoard, GameStates.Phase.BATTLE);
    }

    @Override
    public BoardsQuery.Boards view(Player player) {
        return view(player, turnOwnerBoard, otherPlayerBoard, BoardsQuery.Phase.BATTLE);
    }
}
