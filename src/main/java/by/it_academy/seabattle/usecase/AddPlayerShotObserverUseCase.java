package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface AddPlayerShotObserverUseCase {

    void add(Observer observer);

    @FunctionalInterface
    interface Observer {

        void onShot(UUID shotOwnerId, UUID targetGridOwnerId, String coordinates);
    }
}
