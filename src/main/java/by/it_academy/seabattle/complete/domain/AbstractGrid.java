package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.port.GameStates;
import by.it_academy.seabattle.complete.usecase.GridsQuery;
import by.it_academy.seabattle.complete.usecase.SquareQuery;

import java.util.Collection;
import java.util.Optional;
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


    SquareQuery.Status square(Set<Ship> ships, Set<Square> checked, Square square) {
        return shipSquareStatus(ships, square).orElseGet(() -> checkedSquareStatus(checked, square));
    }

    private SquareQuery.Status checkedSquareStatus(Set<Square> checked, Square square) {
        if (checked.contains(square)) {
            return SquareQuery.Status.CHECKED;
        }
        return SquareQuery.Status.EMPTY;
    }

    private Optional<SquareQuery.Status> shipSquareStatus(Set<Ship> ships, Square square) {
        return ships.stream()
                .filter(ship -> ship.positionedOn(square))
                .findAny()
                .map(ship -> ship.square(square));
    }
}
