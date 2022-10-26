package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerStates;
import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.exception.DuplicatePlayerId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterNewPlayerUseCaseTest {

    private final RegisterNewPlayerUseCase useCase = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates())
            .registerNewPlayerUseCase();
    private final UUID playerId = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Test
    void givenPlayerIsInQueue_whenRegister_thenPlayerWasNotRegistered() {
        useCase.register(playerId);

        assertThatThrownBy(() -> useCase.register(playerId)).isInstanceOf(DuplicatePlayerId.class);
    }
}