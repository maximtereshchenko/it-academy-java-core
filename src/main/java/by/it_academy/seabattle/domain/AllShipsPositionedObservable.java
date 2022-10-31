package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.AddAllShipsPositionedObserverUseCase;
import by.it_academy.seabattle.usecase.FillGridWithRandomShipsUseCase;
import by.it_academy.seabattle.usecase.PositionShipUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

final class AllShipsPositionedObservable implements PositionShipUseCase, FillGridWithRandomShipsUseCase, AddAllShipsPositionedObserverUseCase {

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
        GameStates.State state = games.findGameByPlayer(players.findPlayer(playerId)).state();
        if (state.phase() != GameStates.Phase.BATTLE) {
            return;
        }
        observers.forEach(observer ->
                observer.onAllShipsPositioned(state.turnOwnerGrid().playerId(), state.otherPlayerGrid().playerId())
        );
    }
}
