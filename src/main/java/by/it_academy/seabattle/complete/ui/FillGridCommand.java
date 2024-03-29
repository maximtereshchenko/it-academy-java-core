package by.it_academy.seabattle.complete.ui;

import by.it_academy.seabattle.complete.usecase.FillGridWithRandomShipsUseCase;
import by.it_academy.seabattle.complete.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.complete.usecase.exception.GridIsComplete;

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
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        return playerIdStorage.get()
                .map(this::fill)
                .orElse("No player ID found");
    }

    private String fill(UUID playerId) {
        try {
            useCase.fill(playerId);
            return "Ships positioned";
        } catch (GameWasNotFound e) {
            return "Game was not found";
        } catch (GridIsComplete e) {
            return "Grid is complete";
        }
    }
}
