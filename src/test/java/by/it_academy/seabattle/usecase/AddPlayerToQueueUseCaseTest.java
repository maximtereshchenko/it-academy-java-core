package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerStates;
import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.exception.GameWithPlayerExists;
import by.it_academy.seabattle.usecase.exception.PlayerIsInQueue;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddPlayerToQueueUseCaseTest {

    private final SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates());
    private final UUID firstPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID secondPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Test
    void givenPlayerIsInQueue_whenAdd_thenPlayerIsInQueueThrown() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
        useCase.add(firstPlayerId);

        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(PlayerIsInQueue.class);
    }

    @Test
    void givenGameStartedWithPlayer_whenRegister_thenGameWithPlayerExistsThrown() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();
        useCase.add(firstPlayerId);
        useCase.add(secondPlayerId);

        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(GameWithPlayerExists.class);
    }

    @Test
    void givenNonexistentPlayerId_whenRegister_thenPlayerWasNotFoundThrown() {
        AddPlayerToQueueUseCase useCase = seaBattle.addPlayerToQueueUseCase();

        assertThatThrownBy(() -> useCase.add(firstPlayerId)).isInstanceOf(PlayerWasNotFound.class);
    }
}