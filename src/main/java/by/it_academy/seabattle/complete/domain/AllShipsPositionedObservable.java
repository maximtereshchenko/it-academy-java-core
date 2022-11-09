package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.FillGridWithRandomShipsUseCase;
import by.it_academy.seabattle.complete.usecase.PositionShipUseCase;
import by.it_academy.seabattle.complete.usecase.AddAllShipsPositionedObserverUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

final class AllShipsPositionedObservable
        implements PositionShipUseCase, FillGridWithRandomShipsUseCase, AddAllShipsPositionedObserverUseCase {

    private final PositionShipUseCase positionShipUseCase;
    private final FillGridWithRandomShipsUseCase fillGridWithRandomShipsUseCase;
    private final Players players;
    private final Games games;
    private final Collection<Observer> observers = new ArrayList<>();

    AllShipsPositionedObservable(
            PositionShipUseCase positionShipUseCase,
            FillGridWithRandomShipsUseCase fillGridWithRandomShipsUseCase,
            Players players,
            Games games
    ) {
        this.positionShipUseCase = positionShipUseCase;
        this.fillGridWithRandomShipsUseCase = fillGridWithRandomShipsUseCase;
        this.players = players;
        this.games = games;
    }

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void position(UUID playerId, Collection<String> shipCoordinates) {
        positionShipUseCase.position(playerId, shipCoordinates);
        notifyIfAllShipsPlaced(playerId);
    }

    @Override
    public void fill(UUID playerId) {
        fillGridWithRandomShipsUseCase.fill(playerId);
        notifyIfAllShipsPlaced(playerId);
    }

    private void notifyIfAllShipsPlaced(UUID playerId) {
        Game game = games.findGameByPlayer(players.findPlayer(playerId));
        if (game.hasNotAllShips()) {
            return;
        }
        observers.forEach(observer -> observer.onAllShipsPositioned(game.firstPlayerId(), game.secondPlayerId()));
    }
}
