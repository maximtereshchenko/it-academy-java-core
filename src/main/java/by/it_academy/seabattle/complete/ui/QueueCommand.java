package by.it_academy.seabattle.complete.ui;

import by.it_academy.seabattle.complete.usecase.exception.GameWithPlayerExists;
import by.it_academy.seabattle.complete.usecase.exception.PlayerIsInQueue;
import by.it_academy.seabattle.complete.usecase.AddPlayerToQueueUseCase;

import java.util.List;
import java.util.UUID;

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
        return playerIdStorage.get()
                .map(this::queue)
                .orElse("No player ID found");
    }

    private String queue(UUID playerId) {
        try {
            useCase.add(playerId);
            return "Player added to the queue";
        } catch (PlayerIsInQueue e) {
            return "You are already in queue";
        } catch (GameWithPlayerExists e) {
            return "Game exists";
        }
    }
}
