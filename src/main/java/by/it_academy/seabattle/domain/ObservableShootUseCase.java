package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.AddPlayerShotObserverUseCase;
import by.it_academy.seabattle.usecase.ShootUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

final class ObservableShootUseCase implements ShootUseCase, AddPlayerShotObserverUseCase {

    private final ShootUseCase original;
    private final Players players;
    private final Games games;
    private final Collection<Observer> observers = new ArrayList<>();

    ObservableShootUseCase(ShootUseCase original, Players players, Games games) {
        this.original = original;
        this.players = players;
        this.games = games;
    }

    @Override
    public void shoot(UUID playerId, String coordinates) {
        original.shoot(playerId, coordinates);
        GameStates.State state = games.findGameByPlayer(players.findPlayer(playerId)).state();
        observers.forEach(observer -> observer.onShot(playerId, otherPlayerId(state, playerId), coordinates));
    }

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    private UUID otherPlayerId(GameStates.State state, UUID playerId) {
        if (state.turnOwnerGrid().playerId().equals(playerId)) {
            return state.otherPlayerGrid().playerId();
        }
        return playerId;
    }
}
