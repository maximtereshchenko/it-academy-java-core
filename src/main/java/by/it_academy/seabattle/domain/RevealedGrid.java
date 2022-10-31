package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.GridsQuery;

import java.util.Set;
import java.util.UUID;

final class RevealedGrid extends AbstractGrid {

    private final Set<Ship> ships;
    private final Set<Square> checked;

    RevealedGrid(UUID ownerId, Set<Ship> ships, Set<Square> checked) {
        super(ownerId);
        this.ships = Set.copyOf(ships);
        this.checked = Set.copyOf(checked);
    }

    @Override
    public GameStates.Grid state() {
        return state(ships, checked);
    }

    @Override
    public Set<GridsQuery.SquareView> view(Player player) {
        return view(ships, checked, squareView -> true);
    }
}