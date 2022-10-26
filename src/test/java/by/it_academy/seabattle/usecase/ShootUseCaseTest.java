package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerStates;
import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.exception.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShootUseCaseTest {

    private final SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates());
    private final UUID firstPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID secondPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Test
    void givenNonexistentPlayerId_whenShoot_thenPlayerWasNotFoundThrown() {
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a1"))
                .isInstanceOf(PlayerWasNotFound.class);
    }

    @Test
    void givenNoGameExist_whenShoot_thenGameWasNotFoundThrown() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a1"))
                .isInstanceOf(GameWasNotFound.class);
    }

    @Test
    void givenGameIsInStatusShipPlacement_whenShoot_thenBattleHasNorBeenStartedThrown() {
        startGame();
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a1"))
                .isInstanceOf(BattleHasNotBeenStarted.class);
    }

    @Test
    void givenInvalidCoordinates_whenShoot_thenCoordinatesIsNotValidThrown() {
        startGame();
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a0"))
                .isInstanceOf(CoordinatesIsNotValid.class);
    }

    @Test
    void givenTurnIsNotOwnedByPlayer_whenShoot_thenNotYourTurnThrown() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(secondPlayerId, "a1"))
                .isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerTargetedEmptyCell_whenShoot_thenCellHasStatusMiss() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);

        seaBattle.shootUseCase().shoot(firstPlayerId, "a2");

        BoardsQuery.Boards firstPlayerBoards = seaBattle.boardsQuery().getCurrentBoards(firstPlayerId);
        BoardsQuery.Boards secondPlayerBoards = seaBattle.boardsQuery().getCurrentBoards(secondPlayerId);
        assertThat(firstPlayerBoards.ownedCells()).containsExactlyInAnyOrder(shipCells());
        assertThat(firstPlayerBoards.opponentCells())
                .containsExactly(new BoardsQuery.CellView('a', 2, BoardsQuery.Status.EMPTY));
        assertThat(secondPlayerBoards.ownedCells())
                .containsExactlyInAnyOrder(
                        shipCellsAnd(new BoardsQuery.CellView('a', 2, BoardsQuery.Status.EMPTY))
                );
        assertThat(secondPlayerBoards.opponentCells()).isEmpty();
    }

    @Test
    void givenPlayerMissed_whenShoot_thenNotYourTurnThrown() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a2");

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a2")).isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerHitShip_whenShoot_thenPlayerCanMakeAnotherShot() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a3");

        assertThatThrownBy(() -> useCase.shoot(secondPlayerId, "a2")).isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerDestroyedShip_whenShoot_thenCellsAroundItAreVisibleAndEmpty() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a1");

        BoardsQuery.Boards firstPlayerBoards = seaBattle.boardsQuery().getCurrentBoards(firstPlayerId);
        assertThat(firstPlayerBoards.ownedCells()).containsExactlyInAnyOrder(shipCells());
        assertThat(firstPlayerBoards.opponentCells())
                .containsExactlyInAnyOrder(
                        new BoardsQuery.CellView('a', 1, BoardsQuery.Status.DESTROYED_SHIP_SEGMENT),
                        new BoardsQuery.CellView('a', 2, BoardsQuery.Status.EMPTY),
                        new BoardsQuery.CellView('b', 2, BoardsQuery.Status.EMPTY),
                        new BoardsQuery.CellView('b', 1, BoardsQuery.Status.EMPTY)
                );
    }

    @Test
    void givenPlayerDestroyedAllShips_whenShoot_thenGameIsOver() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        destroyAllEnemyShips();

        BoardsQuery.Boards boards = seaBattle.boardsQuery().getCurrentBoards(firstPlayerId);
        assertThat(boards.phase()).isEqualTo(BoardsQuery.Phase.OVER);
        assertThat(boards.winner()).contains(firstPlayerId);
        assertThat(seaBattle.boardsQuery().getCurrentBoards(secondPlayerId).opponentCells())
                .containsExactlyInAnyOrder(shipCells());
    }

    private void destroyAllEnemyShips() {
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a1");
        useCase.shoot(firstPlayerId, "a3");
        useCase.shoot(firstPlayerId, "a5");
        useCase.shoot(firstPlayerId, "a7");
        useCase.shoot(firstPlayerId, "c1");
        useCase.shoot(firstPlayerId, "d1");
        useCase.shoot(firstPlayerId, "c3");
        useCase.shoot(firstPlayerId, "d3");
        useCase.shoot(firstPlayerId, "c5");
        useCase.shoot(firstPlayerId, "d5");
        useCase.shoot(firstPlayerId, "f1");
        useCase.shoot(firstPlayerId, "g1");
        useCase.shoot(firstPlayerId, "h1");
        useCase.shoot(firstPlayerId, "f3");
        useCase.shoot(firstPlayerId, "g3");
        useCase.shoot(firstPlayerId, "h3");
        useCase.shoot(firstPlayerId, "f5");
        useCase.shoot(firstPlayerId, "g5");
        useCase.shoot(firstPlayerId, "h5");
        useCase.shoot(firstPlayerId, "i5");
    }

    private BoardsQuery.CellView[] shipCellsAnd(BoardsQuery.CellView... cellViews) {
        return Stream.concat(
                        Arrays.stream(shipCells()),
                        Arrays.stream(cellViews)
                )
                .toArray(BoardsQuery.CellView[]::new);
    }

    private void placeAllShips(UUID playerId) {
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();

        useCase.place(playerId, List.of("a1"));
        useCase.place(playerId, List.of("a3"));
        useCase.place(playerId, List.of("a5"));
        useCase.place(playerId, List.of("a7"));

        useCase.place(playerId, List.of("c1", "d1"));
        useCase.place(playerId, List.of("c3", "d3"));
        useCase.place(playerId, List.of("c5", "d5"));

        useCase.place(playerId, List.of("f1", "g1", "h1"));
        useCase.place(playerId, List.of("f3", "g3", "h3"));

        useCase.place(playerId, List.of("f5", "g5", "h5", "i5"));
    }

    private void startGame() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(firstPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(secondPlayerId);
    }

    private BoardsQuery.CellView[] shipCells() {
        return new BoardsQuery.CellView[]{
                new BoardsQuery.CellView('a', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('a', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('a', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('a', 7, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('c', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('d', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('c', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('d', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('c', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('d', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('f', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('g', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('h', 1, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('f', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('g', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('h', 3, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('f', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('g', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('h', 5, BoardsQuery.Status.SHIP_SEGMENT),
                new BoardsQuery.CellView('i', 5, BoardsQuery.Status.SHIP_SEGMENT)
        };
    }
}