package by.it_academy.seabattle.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

final class InMemoryChats implements Chats {

    private final Map<Long, UUID> map = new HashMap<>();

    @Override
    public Optional<UUID> playerId(long chatId) {
        return Optional.ofNullable(map.get(chatId));
    }

    @Override
    public void save(long chatId, UUID playerId) {
        map.put(chatId, playerId);
    }
}
