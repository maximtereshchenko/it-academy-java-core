package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.DuplicatePlayerId;

import java.util.UUID;

public interface RegisterNewPlayerUseCase {

    void register(UUID playerId) throws DuplicatePlayerId;
}
