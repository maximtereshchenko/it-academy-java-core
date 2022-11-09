package by.it_academy.seabattle.complete.ui;

import by.it_academy.seabattle.complete.usecase.ShootUseCase;
import by.it_academy.seabattle.complete.usecase.SquareQuery;
import by.it_academy.seabattle.complete.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.complete.usecase.exception.NotYourTurn;
import by.it_academy.seabattle.complete.usecase.exception.SquareIsNotValid;

import java.util.List;
import java.util.UUID;

public final class ShootCommand implements Command {

    private final ShootUseCase useCase;
    private final SquareQuery squareQuery;

    public ShootCommand(ShootUseCase useCase, SquareQuery squareQuery) {
        this.useCase = useCase;
        this.squareQuery = squareQuery;
    }

    @Override
    public String name() {
        return "shoot";
    }

    @Override
    public String help() {
        return "Shoot at opponent's grid";
    }

    @Override
    public String execute(PlayerIdStorage playerIdStorage, List<String> arguments) {
        if (arguments.size() != 1) {
            return "One argument expected";
        }
        return playerIdStorage.get()
                .map(playerId -> shoot(playerId, arguments.get(0)))
                .orElse("No player ID found");
    }

    private String shoot(UUID playerId, String coordinates) {
        try {
            useCase.shoot(playerId, coordinates);
            SquareQuery.Status status = squareQuery.square(playerId, coordinates);
            if (status == SquareQuery.Status.DESTROYED_SHIP_SEGMENT) {
                return "Hit";
            }
            if (status == SquareQuery.Status.CHECKED) {
                return "Miss";
            }
            return "";
        } catch (GameWasNotFound e) {
            return "Game was not found";
        } catch (SquareIsNotValid e) {
            return "Square is not valid";
        } catch (NotYourTurn e) {
            return "It is not your turn";
        }
    }
}
