package by.it_academy.seabattle.complete.domain;

interface BattleGrid extends Grid {

    BattleGrid shoot(Square square);

    boolean allShipsSunk();

    boolean hasShipPositionedOn(Square square);

    RevealedGrid reveal();
}
