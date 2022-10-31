package by.it_academy.seabattle.ui;

import by.it_academy.seabattle.usecase.ShootUseCase;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;

import java.util.List;
import java.util.UUID;

public final class ShootCommand implements Command {

    private final ShootUseCase useCase;

    public ShootCommand(ShootUseCase useCase) {
        this.useCase = useCase;
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
    public String execute(UUID playerId, List<String> arguments) {
        try {
            useCase.shoot(playerId, arguments.get(0));
            return "Shot was fired";
        } catch (GameWasNotFound e) {
            return "Game was not found";
        } catch (SquareIsNotValid e) {
            return "Square is not valid";
        } catch (NotYourTurn e) {
            return "It is not your turn";
        }
    }
}
