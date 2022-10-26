package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.*;

import java.util.UUID;

public interface ShootUseCase {

    void shoot(UUID playerId, String coordinates)
            throws PlayerWasNotFound, GameWasNotFound, BattleHasNotBeenStarted, CoordinatesIsNotValid, NotYourTurn;
}
