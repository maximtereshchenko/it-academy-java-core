package by.it_academy.seabattle.complete.application;

import by.it_academy.seabattle.complete.port.PlayerIds;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class InMemoryPlayerIds implements PlayerIds {

    private final Set<UUID> set = new HashSet<>();

    @Override
    public boolean has(UUID id) {
        return set.contains(id);
    }

    @Override
    public void save(UUID id) {
        set.add(id);
    }
}
