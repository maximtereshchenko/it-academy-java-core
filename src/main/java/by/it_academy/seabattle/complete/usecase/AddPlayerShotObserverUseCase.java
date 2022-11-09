package by.it_academy.seabattle.complete.usecase;

import java.util.UUID;

public interface AddPlayerShotObserverUseCase {

    void add(Observer observer);

    @FunctionalInterface
    interface Observer {

        void onShot(UUID shotOwnerId, UUID targetGridOwnerId, String coordinates);
    }
}
