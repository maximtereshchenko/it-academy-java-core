package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.AddGameStartedObserverUseCase;
import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;

import java.util.*;

class Queue {

    private final Set<Player> players = new HashSet<>();
    private final Collection<AddGameStartedObserverUseCase.Observer> observers = new ArrayList<>();
    private final Games games;

    Queue(Games games) {
        this.games = games;
    }

    void register(Player player) {
        if (!players.add(player)) {
            throw new PlayerIsInQueue();
        }
        if (players.size() < 2) {
            return;
        }
        Iterator<Player> iterator = players.iterator();
        Player firstPlayer = iterator.next();
        Player secondPlayer = iterator.next();
        games.save(new ShipPositioningPhase(firstPlayer, secondPlayer));
        players.remove(firstPlayer);
        players.remove(secondPlayer);
        observers.forEach(observer -> observer.onGameStarted(firstPlayer.id(), secondPlayer.id()));
    }

    void addObserver(AddGameStartedObserverUseCase.Observer observer) {
        observers.add(observer);
    }
}
