package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.UnexpectedException;

import java.util.Collection;
import java.util.stream.Collectors;

final class Games {

    private final GameStates gameStates;
    private final ShipFactory shipFactory;

    Games(GameStates gameStates, ShipFactory shipFactory) {
        this.gameStates = gameStates;
        this.shipFactory = shipFactory;
    }

    ShipPositioningPhase findGameInShipPositioningPhaseByPlayer(Player player) {
        return gameStates.findByPlayerIdAndPhase(player.id(), GameStates.Phase.SHIP_POSITIONING)
                .map(this::shipPositioningPhase)
                .orElseThrow(GameWasNotFound::new);
    }

    void save(Game game) {
        gameStates.save(game.state());
    }

    boolean hasActiveGameWith(Player player) {
        return gameStates.existsByPlayerIdAndPhaseNotOver(player.id());
    }

    Game findGameByPlayer(Player player) {
        return gameStates.findByPlayerId(player.id())
                .map(this::game)
                .orElseThrow(GameWasNotFound::new);
    }

    BattlePhase findGameInBattlePhaseByPlayer(Player player) {
        return gameStates.findByPlayerIdAndPhase(player.id(), GameStates.Phase.BATTLE)
                .map(this::battlePhase)
                .orElseThrow(GameWasNotFound::new);
    }

    private Game game(GameStates.State state) {
        switch (state.phase()) {
            case SHIP_POSITIONING:
                return shipPositioningPhase(state);
            case BATTLE:
                return battlePhase(state);
            case OVER:
                return gameOverPhase(state);
            default:
                throw new UnexpectedException("Unknown phase: " + state.phase());
        }
    }

    private GameOverPhase gameOverPhase(GameStates.State state) {
        return new GameOverPhase(
                state.id(),
                battleGrid(state.turnOwnerGrid()).reveal(),
                battleGrid(state.otherPlayerGrid()).reveal()
        );
    }

    private BattlePhase battlePhase(GameStates.State state) {
        return new BattlePhase(
                state.id(),
                battleGrid(state.turnOwnerGrid()),
                battleGrid(state.otherPlayerGrid())
        );
    }

    private BattleGrid battleGrid(GameStates.Grid grid) {
        SomeShipsFloat battleGrid = new SomeShipsFloat(
                grid.playerId(),
                grid.ships()
                        .stream()
                        .map(shipFactory::ship)
                        .collect(Collectors.toSet()),
                grid.checkedSquares()
                        .stream()
                        .map(square -> Square.of(square.column(), square.row()))
                        .collect(Collectors.toSet())
        );
        if (allShipSegmentsDestroyed(grid)) {
            return new AllShipsSunk(battleGrid);
        }
        return battleGrid;
    }

    private boolean allShipSegmentsDestroyed(GameStates.Grid grid) {
        return grid.ships()
                .stream()
                .map(GameStates.Ship::destroyedSquares)
                .mapToLong(Collection::size)
                .sum() == 20;
    }

    private ShipPositioningPhase shipPositioningPhase(GameStates.State state) {
        return new ShipPositioningPhase(
                state.id(),
                shipPositioningGrid(state.turnOwnerGrid()),
                shipPositioningGrid(state.otherPlayerGrid())
        );
    }

    private ShipPositioningGrid shipPositioningGrid(GameStates.Grid grid) {
        ShipPositioningGrid identity = new IncompleteGrid(grid.playerId());
        return grid.ships()
                .stream()
                .map(shipFactory::intactShip)
                .reduce(identity, ShipPositioningGrid::position, (first, second) -> first);
    }
}
