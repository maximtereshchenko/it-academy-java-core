package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.NotYourTurn;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShootUseCaseTest extends SeaBattleTest {

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
    void givenGameIsInStatusShipPlacement_whenShoot_thenGameWasNotFoundThrown() {
        startGame();
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a1"))
                .isInstanceOf(GameWasNotFound.class);
    }

    @Test
    void givenInvalidCoordinates_whenShoot_thenSquareIsNotValidThrown() {
        startBattle();
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a0"))
                .isInstanceOf(SquareIsNotValid.class);
    }

    @Test
    void givenTurnIsNotOwnedByPlayer_whenShoot_thenNotYourTurnThrown() {
        startBattle();
        ShootUseCase useCase = seaBattle.shootUseCase();

        assertThatThrownBy(() -> useCase.shoot(secondPlayerId, "a1"))
                .isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerTargetedEmptyCell_whenShoot_thenCellHasStatusMiss() {
        startBattle();

        seaBattle.shootUseCase().shoot(firstPlayerId, "a2");

        GridsQuery.Grids firstPlayerBoards = seaBattle.boardsQuery().currentGrids(firstPlayerId);
        GridsQuery.Grids secondPlayerBoards = seaBattle.boardsQuery().currentGrids(secondPlayerId);
        assertThat(firstPlayerBoards.ownedSquares()).containsExactlyInAnyOrder(shipSegments());
        assertThat(firstPlayerBoards.opponentSquares())
                .containsExactly(new GridsQuery.SquareView('a', 2, GridsQuery.Status.EMPTY));
        assertThat(secondPlayerBoards.ownedSquares())
                .containsExactlyInAnyOrder(
                        expectedSquareViews()
                );
        assertThat(secondPlayerBoards.opponentSquares()).isEmpty();
    }

    @Test
    void givenPlayerMissed_whenShoot_thenNotYourTurnThrown() {
        startBattle();
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a2");

        assertThatThrownBy(() -> useCase.shoot(firstPlayerId, "a2")).isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerHitShip_whenShoot_thenPlayerCanMakeAnotherShot() {
        startBattle();
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a3");

        assertThatThrownBy(() -> useCase.shoot(secondPlayerId, "a2")).isInstanceOf(NotYourTurn.class);
    }

    @Test
    void givenPlayerDestroyedShip_whenShoot_thenCellsAroundItAreVisibleAndEmpty() {
        startBattle();
        ShootUseCase useCase = seaBattle.shootUseCase();
        useCase.shoot(firstPlayerId, "a1");

        GridsQuery.Grids firstPlayerBoards = seaBattle.boardsQuery().currentGrids(firstPlayerId);
        assertThat(firstPlayerBoards.ownedSquares()).containsExactlyInAnyOrder(shipSegments());
        assertThat(firstPlayerBoards.opponentSquares())
                .containsExactlyInAnyOrder(
                        new GridsQuery.SquareView('a', 1, GridsQuery.Status.DESTROYED_SHIP_SEGMENT),
                        new GridsQuery.SquareView('a', 2, GridsQuery.Status.EMPTY),
                        new GridsQuery.SquareView('b', 2, GridsQuery.Status.EMPTY),
                        new GridsQuery.SquareView('b', 1, GridsQuery.Status.EMPTY)
                );
    }

    @Test
    void givenPlayerDestroyedAllShips_whenShoot_thenGameIsOver() {
        startBattle();
        destroyAllEnemyShips();

        GridsQuery.Grids boards = seaBattle.boardsQuery().currentGrids(firstPlayerId);
        assertThat(boards.phase()).isEqualTo(GridsQuery.Phase.OVER);
        assertThat(boards.winner()).contains(firstPlayerId);
        assertThat(seaBattle.boardsQuery().currentGrids(secondPlayerId).opponentSquares())
                .containsExactlyInAnyOrder(shipSegments());
    }

    private void destroyAllEnemyShips() {
        ships()
                .stream()
                .flatMap(Collection::stream)
                .forEach(square -> seaBattle.shootUseCase().shoot(firstPlayerId, square));
    }

    private GridsQuery.SquareView[] expectedSquareViews() {
        return Stream.concat(
                        Arrays.stream(shipSegments()),
                        Stream.of(new GridsQuery.SquareView('a', 2, GridsQuery.Status.EMPTY))
                )
                .toArray(GridsQuery.SquareView[]::new);
    }
}