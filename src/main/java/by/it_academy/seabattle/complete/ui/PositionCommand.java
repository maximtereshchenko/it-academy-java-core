package by.it_academy.seabattle.complete.ui;

import by.it_academy.seabattle.complete.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.complete.usecase.PositionShipUseCase;
import by.it_academy.seabattle.complete.usecase.exception.ShipIsNotValid;
import by.it_academy.seabattle.complete.usecase.exception.SquareIsNotValid;

import java.util.List;
import java.util.UUID;

public final class PositionCommand implements Command {

    private final PositionShipUseCase useCase;

    public PositionCommand(PositionShipUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String name() {
        return "position";
    }

    @Override
    public String help() {
        return "Position a ship on the player's grid";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        return playerIdStorage.get()
                .map(playerId -> position(playerId, arguments))
                .orElse("No player ID found");
    }

    private String position(UUID playerId, List<String> arguments) {
        try {
            useCase.position(playerId, arguments);
            return "Ship positioned";
        } catch (GameWasNotFound e) {
            return "Game was not found";
        } catch (ShipIsNotValid e) {
            return "Ship is not valid";
        } catch (SquareIsNotValid e) {
            return "Square is not valid";
        }
    }
}
