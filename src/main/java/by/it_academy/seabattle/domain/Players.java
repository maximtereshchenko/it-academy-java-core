package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.PlayerIds;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

import java.util.UUID;

final class Players {

    private final PlayerIds playerIds;

    Players(PlayerIds playerIds) {
        this.playerIds = playerIds;
    }

    Player findPlayer(UUID playerId) {
        if (!playerIds.has(playerId)) {
            throw new PlayerWasNotFound();
        }
        return new Player(playerId);
    }

    boolean exists(Player player) {
        return playerIds.has(player.id());
    }

    void save(Player player) {
        playerIds.save(player.id());
    }
}
