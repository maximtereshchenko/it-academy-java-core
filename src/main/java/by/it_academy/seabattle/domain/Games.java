package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

class Games {

    private final GameStates gameStates;
    private final ShipFactory shipFactory;

    Games(GameStates gameStates, ShipFactory shipFactory) {
        this.gameStates = gameStates;
        this.shipFactory = shipFactory;
    }

    Optional<Game> findByPlayer(Player player) {
        return gameStates.findByPlayerId(player.id())
                .map(this::game);
    }

    void save(Game game) {
        gameStates.save(game.state());
    }

    boolean hasGameWith(Player player) {
        return gameStates.findByPlayerId(player.id()).isPresent();
    }

    private Game game(GameStates.State state) {
        switch (state.phase()) {
            case SHIP_PLACEMENT:
                return shipPlacement(state);
            case BATTLE:
                return battle(state);
            case OVER:
                return gameOver(state);
            default:
                return null;
        }
    }

    private Game battle(GameStates.State state) {
        return new Battle(
                state.id(),
                board(state.nextTurnOwnerId(), state.nextTurnOwnerBoard()),
                board(state.otherPlayerId(), state.otherPlayerBoard())
        );
    }

    private Game shipPlacement(GameStates.State state) {
        return new ShipPlacement(
                state.id(),
                board(state.nextTurnOwnerId(), state.nextTurnOwnerBoard()),
                board(state.otherPlayerId(), state.otherPlayerBoard())
        );
    }

    private Game gameOver(GameStates.State state) {
        return new GameOver(
                state.id(),
                board(state.nextTurnOwnerId(), state.nextTurnOwnerBoard()),
                board(state.otherPlayerId(), state.otherPlayerBoard())
        );
    }

    private Board board(UUID id, GameStates.Board board) {
        return new Board(
                id,
                shipFactory.ships(board.ships()),
                board.otherCells()
                        .stream()
                        .map(cell -> new Cell(cell.row(), cell.column()))
                        .collect(Collectors.toSet())
        );
    }
}
