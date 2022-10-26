package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.exception.BattleHasNotBeenStarted;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.UUID;
import java.util.stream.Stream;

class ShipPlacement extends AbstractGame {

    private final Board firstPlayerBoard;
    private final Board secondPlayerBoard;

    ShipPlacement(UUID id, Board firstPlayerBoard, Board secondPlayerBoard) {
        super(id);
        this.firstPlayerBoard = firstPlayerBoard;
        this.secondPlayerBoard = secondPlayerBoard;
    }

    ShipPlacement(Player firstPlayer, Player secondPlayer) {
        this(UUID.randomUUID(), new Board(firstPlayer), new Board(secondPlayer));
    }

    @Override
    public Game placeShip(Player player, Ship ship) {
        Board newFirstPlayerBoard = withPlacedShipIfOwnedBy(firstPlayerBoard, player, ship);
        Board newSecondPlayerBoard = withPlacedShipIfOwnedBy(secondPlayerBoard, player, ship);
        if (newFirstPlayerBoard.isFull() && newSecondPlayerBoard.isFull()) {
            return new Battle(id(), newFirstPlayerBoard, newSecondPlayerBoard);
        }
        return new ShipPlacement(id(), newFirstPlayerBoard, newSecondPlayerBoard);
    }

    @Override
    public Game shoot(Player player, Cell cell) {
        throw new BattleHasNotBeenStarted();
    }

    @Override
    public GameStates.State state() {
        return state(firstPlayerBoard, secondPlayerBoard, GameStates.Phase.SHIP_PLACEMENT);
    }

    @Override
    public BoardsQuery.Boards view(Player player) {
        return view(player, firstPlayerBoard, secondPlayerBoard, BoardsQuery.Phase.SHIP_PLACEMENT);
    }

    private Board withPlacedShipIfOwnedBy(Board board, Player player, Ship ship) {
        if (!board.ownerId().equals(player.id())) {
            return board;
        }
        if (board.canNotBePlaced(ship)) {
            throw new ShipIsNotValid();
        }
        return board.place(ship);
    }
}
