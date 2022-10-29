package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.exception.UnexpectedException;

abstract class AbstractGame implements Game {

    <T extends Grid> T ownedGrid(Player player, T firstGrid, T secondGrid) {
        if (firstGrid.isOwnedBy(player)) {
            return firstGrid;
        }
        if (secondGrid.isOwnedBy(player)) {
            return secondGrid;
        }
        throw new UnexpectedException("Player do not belong to game");
    }

    <T extends Grid> T otherPlayerGrid(Player player, T firstGrid, T secondGrid) {
        if (firstGrid.isOwnedBy(player)) {
            return secondGrid;
        }
        return firstGrid;
    }
}
