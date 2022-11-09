package by.it_academy.seabattle.complete.usecase;

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

    @Test
    void givenShotObserverAdded_whenGameStarted_thenObserverNotified() {
        ShotObserver observer = new ShotObserver();
        seaBattle.addPlayerShotObserverUseCase().add(observer);
        startBattle();

        seaBattle.shootUseCase().shoot(firstPlayerId, "a1");

        assertThat(observer.shotOwnerId).isEqualTo(firstPlayerId);
        assertThat(observer.targetGridOwnerId).isEqualTo(secondPlayerId);
        assertThat(observer.coordinates).isEqualTo("a1");
    }

    @Test
    void givenGameOverObserverAdded_whenGameWasOver_thenObserverNotified() {
        GameOverObserver observer = new GameOverObserver();
        seaBattle.addGameOverObserverUseCase().add(observer);
        startBattle();

        destroyAllEnemyShips();

        assertThat(observer.winnerId).isEqualTo(firstPlayerId);
        assertThat(observer.loserId).isEqualTo(secondPlayerId);
    }

    @Test
    void givenAllShipsPositionedObserverAdded_whenAllShipsPositionedManually_thenObserverNotified() {
        AllShipsPositionedObserver observer = new AllShipsPositionedObserver();
        seaBattle.addAllShipsPositionedObserverUseCase().add(observer);

        startBattle();

        assertThat(observer.firstPlayerWithAllShipsId).isEqualTo(firstPlayerId);
        assertThat(observer.secondPlayerWithAllShipsId).isEqualTo(secondPlayerId);
    }

    @Test
    void givenAllShipsPositionedObserverAdded_whenFillGrid_thenObserverNotified() {
        AllShipsPositionedObserver observer = new AllShipsPositionedObserver();
        seaBattle.addAllShipsPositionedObserverUseCase().add(observer);
        startGame();

        seaBattle.fillGridWithRandomShipsUseCase().fill(firstPlayerId);
        seaBattle.fillGridWithRandomShipsUseCase().fill(secondPlayerId);

        assertThat(observer.firstPlayerWithAllShipsId).isEqualTo(firstPlayerId);
        assertThat(observer.secondPlayerWithAllShipsId).isEqualTo(secondPlayerId);
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

    private static final class ShotObserver implements AddPlayerShotObserverUseCase.Observer {

        private UUID shotOwnerId;
        private UUID targetGridOwnerId;
        private String coordinates;

        @Override
        public void onShot(UUID shotOwnerId, UUID targetGridOwnerId, String coordinates) {
            this.shotOwnerId = shotOwnerId;
            this.targetGridOwnerId = targetGridOwnerId;
            this.coordinates = coordinates;
        }
    }

    private static final class GameOverObserver implements AddGameOverObserverUseCase.Observer {

        private UUID winnerId;
        private UUID loserId;

        @Override
        public void onGameOver(UUID winnerId, UUID loserId) {
            this.winnerId = winnerId;
            this.loserId = loserId;
        }
    }

    private static final class AllShipsPositionedObserver implements AddAllShipsPositionedObserverUseCase.Observer {

        private UUID firstPlayerWithAllShipsId;
        private UUID secondPlayerWithAllShipsId;

        @Override
        public void onAllShipsPositioned(UUID firstPlayerId, UUID secondPlayerId) {
            firstPlayerWithAllShipsId = firstPlayerId;
            secondPlayerWithAllShipsId = secondPlayerId;
        }
    }
}
