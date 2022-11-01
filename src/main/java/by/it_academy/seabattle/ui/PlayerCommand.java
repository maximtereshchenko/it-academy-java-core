package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.RegisterNewPlayerUseCase;

import java.util.List;
import java.util.UUID;

public final class PlayerCommand implements Command {

    public static final String NAME = "player";

    private final RegisterNewPlayerUseCase useCase;

    public PlayerCommand(RegisterNewPlayerUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String help() {
        return "Register new player, or log in, if player ID argument is present";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        if (arguments.isEmpty()) {
            UUID id = UUID.randomUUID();
            useCase.register(id);
            playerIdStorage.set(id);
            return id.toString();
        }
        playerIdStorage.set(UUID.fromString(arguments.get(0)));
        return playerIdStorage.get().toString();
    }
}
