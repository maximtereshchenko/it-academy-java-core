package by.it_academy.seabattle.complete.domain;

interface ShipPositioningGrid extends Grid {

    boolean isComplete();

    ShipPositioningGrid position(IntactShip ship);

    boolean canNotBePositioned(IntactShip ship);

    BattleGrid battleGrid();
}
