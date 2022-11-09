package by.it_academy.seabattle.complete.usecase;

import by.it_academy.seabattle.complete.usecase.exception.GridIsComplete;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FillGridWithRandomShipsUseCaseTest extends SeaBattleTest {

    @Test
    void givenGameExists_whenFillGrid_thenGridHasAllShips() {
        startGame();

        seaBattle.fillGridWithRandomShipsUseCase().fill(firstPlayerId);
        seaBattle.fillGridWithRandomShipsUseCase().fill(secondPlayerId);

        assertThat(seaBattle.boardsQuery().currentGrids(firstPlayerId).phase()).isEqualTo(GridsQuery.Phase.BATTLE);
    }

    @Test
    void givenGridIsComplete_whenFillGrid_thenGridIsCompleteThrown() {
        startGame();
        FillGridWithRandomShipsUseCase useCase = seaBattle.fillGridWithRandomShipsUseCase();
        useCase.fill(firstPlayerId);

        assertThatThrownBy(() -> useCase.fill(firstPlayerId)).isInstanceOf(GridIsComplete.class);
    }
}