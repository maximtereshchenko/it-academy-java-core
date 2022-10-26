package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.AddPlayerToQueueUseCase;
import by.it_academy.seabattle.usecase.exception.GameWithPlayerExists;
import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

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
    public void add(UUID playerId) throws PlayerWasNotFound, PlayerIsInQueue {
        Player player = players.findById(playerId).orElseThrow(PlayerWasNotFound::new);
        if (games.hasGameWith(player)) {
            throw new GameWithPlayerExists();
        }
        queue.register(player);
    }
}
