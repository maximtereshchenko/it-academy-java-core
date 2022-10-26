package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.GameWithPlayerExists;
import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

import java.util.UUID;

public interface AddPlayerToQueueUseCase {

    void add(UUID playerId) throws PlayerWasNotFound, PlayerIsInQueue, GameWithPlayerExists;
}
