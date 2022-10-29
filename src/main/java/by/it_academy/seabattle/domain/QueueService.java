package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.AddPlayerToQueueUseCase;
import by.it_academy.seabattle.usecase.exception.GameWithPlayerExists;

import java.util.UUID;

class QueueService implements AddPlayerToQueueUseCase {

    private final Players players;
    private final Games games;
    private final Queue queue;

    QueueService(Players players, Games games, Queue queue) {
        this.players = players;
        this.games = games;
        this.queue = queue;
    }

    @Override
    public void add(UUID playerId) {
        Player player = players.findPlayer(playerId);
        if (games.hasActiveGameWith(player)) {
            throw new GameWithPlayerExists();
        }
        queue.register(player);
    }
}
