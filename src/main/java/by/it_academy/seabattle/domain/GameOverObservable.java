package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.AddGameOverObserverUseCase;
import by.it_academy.seabattle.usecase.ShootUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

final class GameOverObservable implements ShootUseCase, AddGameOverObserverUseCase {

    private final ShootUseCase original;
    private final Players players;
    private final Games games;
    private final Collection<Observer> observers = new ArrayList<>();

    GameOverObservable(ShootUseCase original, Players players, Games games) {
        this.original = original;
        this.players = players;
        this.games = games;
    }

    @Override
    public void shoot(UUID playerId, String coordinates) {
        original.shoot(playerId, coordinates);
        Game game = games.findGameByPlayer(players.findPlayer(playerId));
        if (game.isNotOver()) {
            return;
        }
        observers.forEach(observer ->
                observer.onGameOver(game.firstPlayerId(), game.secondPlayerId())
        );
    }

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }
}
