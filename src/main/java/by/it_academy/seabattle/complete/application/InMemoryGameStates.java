package by.it_academy.seabattle.complete.application;

import by.it_academy.seabattle.complete.port.GameStates;

import java.util.*;

public final class InMemoryGameStates implements GameStates {

    private final Map<UUID, State> map = new HashMap<>();

    @Override
    public Optional<State> findByPlayerIdAndPhase(UUID playerId, Phase phase) {
        return findByPlayerId(playerId)
                .filter(state -> state.phase() == phase);
    }

    @Override
    public Optional<State> findByPlayerId(UUID playerId) {
        return map.values()
                .stream()
                .filter(state -> hasPlayer(playerId, state))
                .max(Comparator.comparing(GameStates.State::startedAt));
    }

    @Override
    public boolean existsByPlayerIdAndPhaseNotOver(UUID playerId) {
        return map.values()
                .stream()
                .filter(state -> state.phase() != Phase.OVER)
                .anyMatch(state -> hasPlayer(playerId, state));
    }

    @Override
    public void save(State state) {
        map.put(state.id(), state);
    }

    private boolean hasPlayer(UUID playerId, State state) {
        return state.turnOwnerGrid().playerId().equals(playerId) || state.otherPlayerGrid().playerId().equals(playerId);
    }
}
