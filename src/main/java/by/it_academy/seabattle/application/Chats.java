package by.it_academy.seabattle.application;

import java.util.Optional;
import java.util.UUID;

interface Chats {

    Optional<UUID> playerId(long chatId);

    Optional<Long> chatId(UUID playerId);

    void save(long chatId, UUID playerId);
}
