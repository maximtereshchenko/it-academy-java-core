package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface ShootUseCase {

    void shoot(UUID playerId, String coordinates);
}
