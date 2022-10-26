package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.CoordinatesIsNotValid;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;

import java.util.Collection;
import java.util.UUID;

public interface PlaceShipUseCase {

    void place(UUID playerId, Collection<String> shipCoordinates)
            throws PlayerWasNotFound, GameWasNotFound, CoordinatesIsNotValid, ShipIsNotValid;
}
