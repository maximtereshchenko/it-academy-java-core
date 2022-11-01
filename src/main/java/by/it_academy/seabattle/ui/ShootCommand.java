package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.ShootUseCase;
import by.it_academy.seabattle.usecase.SquareQuery;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;

import java.util.List;

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
        try {
            useCase.shoot(playerIdStorage.get(), arguments.get(0));
            SquareQuery.Status status = squareQuery.square(playerIdStorage.get(), arguments.get(0));
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
