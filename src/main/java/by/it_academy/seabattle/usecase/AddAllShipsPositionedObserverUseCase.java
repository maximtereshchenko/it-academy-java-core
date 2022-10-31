package by.it_academy.seabattle.usecase;

import java.util.UUID;

public interface AddAllShipsPositionedObserverUseCase {

    void add(Observer observer);

    @FunctionalInterface
    interface Observer {

        void onAllShipsPositioned(UUID firstPlayerId, UUID secondPlayerId);
    }
}
