package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.PlayerIds;

import java.util.Optional;
import java.util.UUID;

class Players {

    private final PlayerIds playerIds;

    Players(PlayerIds playerIds) {
        this.playerIds = playerIds;
    }

    Optional<Player> findById(UUID id) {
        if (playerIds.exists(id)) {
            return Optional.of(new Player(id));
        }
        return Optional.empty();
    }

    void save(Player player) {
        playerIds.save(player.id());
    }
}
