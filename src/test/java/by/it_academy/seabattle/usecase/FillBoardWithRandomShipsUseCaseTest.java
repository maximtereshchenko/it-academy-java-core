package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerStates;
import by.it_academy.seabattle.domain.SeaBattle;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FillBoardWithRandomShipsUseCaseTest {

    private final SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates());
    private final UUID firstPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID secondPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Test
    void givenGameExists_whenFillBoard_thenBoardHasAllShips() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(firstPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(secondPlayerId);

        seaBattle.fillBoardWithRandomShipsUseCase().fill(firstPlayerId);
        seaBattle.fillBoardWithRandomShipsUseCase().fill(secondPlayerId);

        assertThat(seaBattle.boardsQuery().getCurrentBoards(firstPlayerId).phase()).isEqualTo(BoardsQuery.Phase.BATTLE);
    }
}