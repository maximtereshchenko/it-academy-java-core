package by.it_academy.seabattle.complete.usecase;

import java.util.Collection;
import java.util.UUID;

public interface PositionShipUseCase {

    void position(UUID playerId, Collection<String> shipCoordinates);
}
