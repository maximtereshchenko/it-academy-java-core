package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface AddGameOverObserverUseCase {

    void add(Observer observer);

    @FunctionalInterface
    interface Observer {

        void onGameOver(UUID winnerId, UUID loserId);
    }
}
