package by.it_academy.seabattle.application;

import by.it_academy.seabattle.port.GameStates;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryGameStates implements GameStates {

    private final Map<UUID, State> states = new HashMap<>();

    @Override
    public Optional<State> findByPlayerId(UUID playerId) {
        return states.values()
                .stream()
                .filter(state -> state.nextTurnOwnerId().equals(playerId) || state.otherPlayerId().equals(playerId))
                .findAny();
    }

    @Override
    public void save(State state) {
        states.put(state.id(), state);
    }
}
