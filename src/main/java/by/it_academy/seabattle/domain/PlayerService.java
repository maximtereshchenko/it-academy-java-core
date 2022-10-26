package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.RegisterNewPlayerUseCase;
import by.it_academy.seabattle.usecase.exception.DuplicatePlayerId;

import java.util.UUID;

class PlayerService implements RegisterNewPlayerUseCase {

    private final Players players;

    PlayerService(Players players) {
        this.players = players;
    }

    @Override
    public void register(UUID playerId) throws DuplicatePlayerId {
        if (players.findById(playerId).isPresent()) {
            throw new DuplicatePlayerId();
        }
        players.save(new Player(playerId));
    }
}
