package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.AddPlayerShotObserverUseCase;
import by.it_academy.seabattle.complete.usecase.ShootUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

final class PlayerShotObservable implements ShootUseCase, AddPlayerShotObserverUseCase {

    private final ShootUseCase original;
    private final Players players;
    private final Games games;
    private final Collection<Observer> observers = new ArrayList<>();

    PlayerShotObservable(ShootUseCase original, Players players, Games games) {
        this.original = original;
        this.players = players;
        this.games = games;
    }

    @Override
    public void shoot(UUID playerId, String coordinates) {
        original.shoot(playerId, coordinates);
        Game game = games.findGameByPlayer(players.findPlayer(playerId));
        observers.forEach(observer -> observer.onShot(playerId, otherPlayerId(game, playerId), coordinates));
    }

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    private UUID otherPlayerId(Game game, UUID playerId) {
        if (game.firstPlayerId().equals(playerId)) {
            return game.secondPlayerId();
        }
        return game.firstPlayerId();
    }
}
