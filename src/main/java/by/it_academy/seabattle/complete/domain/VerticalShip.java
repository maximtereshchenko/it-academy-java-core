package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.exception.ShipIsNotValid;

import java.util.HashSet;
import java.util.Set;

final class VerticalShip extends MultipleSquaresShip {

    VerticalShip(Set<Square> squares) {
        super(squares);
    }

    @Override
    IntactShip growAfterGuard(Square square) {
        if (!squares().first().connectedVertically(square) && !squares().last().connectedVertically(square)) {
            throw new ShipIsNotValid();
        }
        Set<Square> copy = new HashSet<>(squares());
        copy.add(square);
        return new VerticalShip(copy);
    }
}
