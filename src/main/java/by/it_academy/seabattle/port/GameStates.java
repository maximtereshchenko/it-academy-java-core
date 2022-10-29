package by.it_academy.seabattle.port;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface GameStates {

    Optional<State> findByPlayerIdAndPhase(UUID playerId, Phase phase);

    Optional<State> findByPlayerId(UUID playerId);

    boolean existsByPlayerId(UUID playerId);

    void save(State state);

    enum Phase {

        SHIP_POSITIONING, BATTLE, OVER
    }

    record State(
            UUID id,
            Phase phase,
            Grid turnOwnerGrid,
            Grid otherPlayerGrid
    ) {}

    record Grid(UUID playerId, Set<Ship> ships, Set<Square> checkedSquares) {}

    record Ship(Set<Square> intactSquares, Set<Square> destroyedSquares) {}

    record Square(char column, int row) {}
}
