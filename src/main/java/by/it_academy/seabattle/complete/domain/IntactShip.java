package by.it_academy.seabattle.complete.domain;

interface IntactShip extends Ship {

    IntactShip grow(Square square);

    boolean overlaps(IntactShip ship);
}
