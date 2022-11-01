package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.AddPlayerToQueueUseCase;
import by.it_academy.seabattle.usecase.exception.GameWithPlayerExists;
import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;

import java.util.List;

public final class QueueCommand implements Command {

    private final AddPlayerToQueueUseCase useCase;

    public QueueCommand(AddPlayerToQueueUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String name() {
        return "queue";
    }

    @Override
    public String help() {
        return "Queue to be matched with another player";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        try {
            useCase.add(playerIdStorage.get());
            return "Player added to the queue";
        } catch (PlayerIsInQueue e) {
            return "You are already in queue";
        } catch (GameWithPlayerExists e) {
            return "Game exists";
        }
    }
}
