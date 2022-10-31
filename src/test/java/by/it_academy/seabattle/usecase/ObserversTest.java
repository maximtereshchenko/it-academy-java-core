package by.it_academy.seabattle.usecase;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

final class ObserversTest extends SeaBattleTest {

    @Test
    void givenGameStartedObserverAdded_whenGameStarted_thenObserverNotified() {
        GameStartedObserver observer = new GameStartedObserver();
        seaBattle.addGameStartedObserverUseCase().add(observer);
        startGame();

        assertThat(observer.firstPlayerIdStartedGame).isEqualTo(firstPlayerId);
        assertThat(observer.secondPlayerIdStartedGame).isEqualTo(secondPlayerId);
    }

    private static final class GameStartedObserver implements AddGameStartedObserverUseCase.Observer {

        private UUID firstPlayerIdStartedGame;
        private UUID secondPlayerIdStartedGame;

        @Override
        public void onGameStarted(UUID firstPlayerId, UUID secondPlayerId) {
            firstPlayerIdStartedGame = firstPlayerId;
            secondPlayerIdStartedGame = secondPlayerId;
        }
    }
}
