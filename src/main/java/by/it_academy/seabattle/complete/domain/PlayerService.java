package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.RegisterNewPlayerUseCase;
import by.it_academy.seabattle.complete.usecase.exception.DuplicatePlayerId;

import java.util.UUID;

class PlayerService implements RegisterNewPlayerUseCase {

    private final Players players;

    PlayerService(Players players) {
        this.players = players;
    }

    @Override
    public void register(UUID playerId) {
        Player player = new Player(playerId);
        if (players.exists(player)) {
            throw new DuplicatePlayerId();
        }
        players.save(player);
    }
}
