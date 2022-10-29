package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Queue {

    private final Set<Player> players = new HashSet<>();
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
    }
}
