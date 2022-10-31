package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.FillGridWithRandomShipsUseCase;
import by.it_academy.seabattle.usecase.exception.GridIsComplete;

import java.util.List;
import java.util.UUID;

public final class FillGridCommand implements Command {

    private final FillGridWithRandomShipsUseCase useCase;

    public FillGridCommand(FillGridWithRandomShipsUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String name() {
        return "fill";
    }

    @Override
    public String help() {
        return "Position random ships on player's grid until it's full";
    }

    @Override
    public String execute(UUID playerId, List<String> arguments) {
        try {
            useCase.fill(playerId);
            return "Ships positioned";
        } catch (GridIsComplete e) {
            return "Grid is complete";
        }
    }
}
