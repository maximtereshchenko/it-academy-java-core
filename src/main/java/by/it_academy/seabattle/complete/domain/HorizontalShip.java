package by.it_academy.seabattle.complete.domain;

import by.it_academy.seabattle.complete.usecase.exception.SquareIsNotValid;

import java.util.HashSet;
import java.util.Set;

final class HorizontalShip extends MultipleSquaresShip {

    HorizontalShip(Set<Square> squares) {
        super(squares);
    }

    @Override
    IntactShip growAfterGuard(Square square) {
        if (!squares().first().connectedHorizontally(square) && !squares().last().connectedHorizontally(square)) {
            throw new SquareIsNotValid();
        }
        Set<Square> copy = new HashSet<>(squares());
        copy.add(square);
        return new HorizontalShip(copy);
    }
}
