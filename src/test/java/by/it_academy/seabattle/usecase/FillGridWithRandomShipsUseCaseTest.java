package by.it_academy.seabattle.usecase;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FillGridWithRandomShipsUseCaseTest extends SeaBattleTest {

    @Test
    void givenGameExists_whenFillGrid_thenGridHasAllShips() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(firstPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(secondPlayerId);

        seaBattle.fillGridWithRandomShipsUseCase().fill(firstPlayerId);
        seaBattle.fillGridWithRandomShipsUseCase().fill(secondPlayerId);

        assertThat(seaBattle.boardsQuery().currentGrids(firstPlayerId).phase()).isEqualTo(GridsQuery.Phase.BATTLE);
    }
}