package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.RegisterNewPlayerUseCase;

import java.util.List;
import java.util.UUID;

public final class RegisterCommand implements Command {

    public static final String NAME = "register";

    private final RegisterNewPlayerUseCase useCase;

    public RegisterCommand(RegisterNewPlayerUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String name() {
        return NAME;
    }

    @Override
    public String help() {
        return "Register new player";
    }

    @Override
    public String execute(UUID playerId, List<String> arguments) {
        UUID id = UUID.randomUUID();
        useCase.register(id);
        return id.toString();
    }
}
