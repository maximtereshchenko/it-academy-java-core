package by.it_academy.seabattle.application;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.exception.UnexpectedException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

final class GameStatesInFiles extends AbstractFilesRepository implements GameStates {

    private final Path rootDirectory = Paths.get("games");
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    GameStatesInFiles() {
        createDirectory(rootDirectory);
    }

    @Override
    public Optional<State> findByPlayerIdAndPhase(UUID playerId, Phase phase) {
        return findByPlayerId(playerId)
                .filter(state -> state.phase() == phase);
    }

    @Override
    public Optional<State> findByPlayerId(UUID playerId) {
        return all()
                .filter(state -> hasPlayer(playerId, state))
                .max(Comparator.comparing(State::startedAt));
    }

    @Override
    public boolean existsByPlayerIdAndPhaseNotOver(UUID playerId) {
        return all()
                .filter(state -> state.phase() != Phase.OVER)
                .anyMatch(state -> hasPlayer(playerId, state));
    }

    @Override
    public void save(State state) {
        try {
            write(rootDirectory.resolve(state.id().toString()), objectMapper.writeValueAsString(state));
        } catch (JsonProcessingException e) {
            throw new UnexpectedException(e);
        }
    }

    private boolean hasPlayer(UUID playerId, State state) {
        return state.turnOwnerGrid().playerId().equals(playerId) || state.otherPlayerGrid().playerId().equals(playerId);
    }

    private Stream<State> all() {
        return all(rootDirectory)
                .map(this::read)
                .map(this::read);
    }

    private State read(String json) {
        try {
            return objectMapper.readValue(json, State.class);
        } catch (JsonProcessingException e) {
            throw new UnexpectedException(e);
        }
    }
}
