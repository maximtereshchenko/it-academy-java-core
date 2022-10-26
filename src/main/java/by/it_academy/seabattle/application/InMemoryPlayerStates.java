package by.it_academy.seabattle.application;

import by.it_academy.seabattle.port.PlayerIds;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InMemoryPlayerStates implements PlayerIds {

    private final Set<UUID> set = new HashSet<>();

    @Override
    public boolean exists(UUID id) {
        return set.contains(id);
    }

    @Override
    public void save(UUID id) {
        set.add(id);
    }
}