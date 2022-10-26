package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

import java.util.UUID;

public interface FillBoardWithRandomShipsUseCase {

    void fill(UUID playerId) throws PlayerWasNotFound, GameWasNotFound;
}
