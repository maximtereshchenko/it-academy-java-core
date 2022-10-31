package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface SquareQuery {

    Status square(UUID playerId, String coordinates);

    enum Status {

        SHIP_SEGMENT, DESTROYED_SHIP_SEGMENT, CHECKED, EMPTY, UNKNOWN
    }
}
