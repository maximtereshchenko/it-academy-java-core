package by.it_academy.seabattle.domain;

interface IntactShip extends Ship {

    IntactShip grow(Square square);

    boolean overlaps(IntactShip ship);
}
