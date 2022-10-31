package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface AddGameStartedObserverUseCase {

    void add(Observer observer);

    @FunctionalInterface
    interface Observer {

        void onGameStarted(UUID firstPlayerId, UUID secondPlayerId);
    }
}
