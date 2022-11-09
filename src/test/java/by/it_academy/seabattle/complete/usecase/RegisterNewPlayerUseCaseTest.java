package by.it_academy.seabattle.complete.usecase;

import by.it_academy.seabattle.complete.usecase.exception.DuplicatePlayerId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegisterNewPlayerUseCaseTest extends SeaBattleTest {

    @Test
    void givenPlayerExists_whenRegister_thenDuplicatePlayerIdThrown() {
        RegisterNewPlayerUseCase useCase = seaBattle.registerNewPlayerUseCase();
        useCase.register(firstPlayerId);

        assertThatThrownBy(() -> useCase.register(firstPlayerId)).isInstanceOf(DuplicatePlayerId.class);
    }
}