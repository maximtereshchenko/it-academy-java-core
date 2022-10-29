package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class AbstractGrid implements Grid {

    private final UUID ownerId;

    AbstractGrid(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public UUID ownerId() {
        return ownerId;
    }

    @Override
    public boolean isOwnedBy(Player player) {
        return player.id().equals(ownerId);
    }

    GameStates.Grid state(Set<? extends Ship> ships, Set<Square> checkedSquares) {
        return new GameStates.Grid(
                ownerId(),
                ships.stream()
                        .map(Ship::state)
                        .collect(Collectors.toSet()),
                checkedSquares.stream()
                        .map(Square::state)
                        .collect(Collectors.toSet())
        );
    }

    GameStates.Grid state(Set<? extends Ship> ships) {
        return state(ships, Set.of());
    }

    Set<GridsQuery.SquareView> view(
            Set<Ship> ships,
            Set<Square> checkedSquares,
            Predicate<GridsQuery.SquareView> predicate
    ) {
        return Stream.concat(
                        ships.stream()
                                .map(Ship::view)
                                .flatMap(Collection::stream),
                        checkedSquares.stream()
                                .map(square -> square.view(GridsQuery.Status.EMPTY))
                )
                .filter(predicate)
                .collect(Collectors.toSet());
    }
}
