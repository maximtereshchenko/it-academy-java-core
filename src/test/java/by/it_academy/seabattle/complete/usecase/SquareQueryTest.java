package by.it_academy.seabattle.complete.usecase;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class SquareQueryTest extends SeaBattleTest {

    @Test
    void givenSquareWasHit_whenSquareQuery_thenStatusIsHit() {
        startBattle();

        seaBattle.shootUseCase().shoot(firstPlayerId, "a1");

        assertThat(seaBattle.squareQuery().square(firstPlayerId, "a1"))
                .isEqualTo(SquareQuery.Status.DESTROYED_SHIP_SEGMENT);
    }

    @Test
    void givenSquareWasEmpty_whenSquareQuery_thenStatusIsMiss() {
        startBattle();

        seaBattle.shootUseCase().shoot(firstPlayerId, "b1");

        assertThat(seaBattle.squareQuery().square(firstPlayerId, "b1"))
                .isEqualTo(SquareQuery.Status.CHECKED);
    }

    @Test
    void givenSquareWasNotRevealed_whenSquareQuery_thenStatusIsUnknown() {
        startBattle();

        assertThat(seaBattle.squareQuery().square(firstPlayerId, "a1")).isEqualTo(SquareQuery.Status.UNKNOWN);
    }

    @Test
    void givenGameIsOverAndSquareHasShip_whenSquareQuery_thenStatusIsShipSegment() {
        startBattle();
        destroyAllEnemyShips();

        assertThat(seaBattle.squareQuery().square(secondPlayerId, "a1"))
                .isEqualTo(SquareQuery.Status.SHIP_SEGMENT);
    }

    @Test
    void givenGameIsOverAndSquareIsEmpty_whenSquareQuery_thenStatusIsShipSegment() {
        startBattle();
        destroyAllEnemyShips();

        assertThat(seaBattle.squareQuery().square(secondPlayerId, "a2"))
                .isEqualTo(SquareQuery.Status.EMPTY);
    }
}
