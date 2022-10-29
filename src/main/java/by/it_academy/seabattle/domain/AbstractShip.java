package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.exception.UnexpectedException;

abstract class AbstractShip implements Ship {

    @Override
    public Ship hit(Square square) {
        if (!positionedOn(square)) {
            throw new UnexpectedException("Square do not belong to ship");
        }
        return hitAfterGuard(square);
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    abstract Ship hitAfterGuard(Square square);
}
