package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;
import by.it_academy.seabattle.usecase.exception.ShipIsNotValid;
import by.it_academy.seabattle.usecase.exception.SquareIsNotValid;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PositionShipUseCaseTest extends SeaBattleTest {

    @Test
    void givenNonexistentPlayerId_whenPositionShip_thenPlayerWasNotFoundThrown() {
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        Collection<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(PlayerWasNotFound.class);
    }

    @Test
    void givenNoGameWithPlayerId_whenPositionShip_thenGameWasNotFoundThrown() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        Collection<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(GameWasNotFound.class);
    }

    @Test
    void givenGameExists_whenPositionShip_thenShipPlaced() {
        startGame();

        seaBattle.positionShipUseCase().position(firstPlayerId, List.of("a1"));

        assertThat(seaBattle.boardsQuery().currentGrids(firstPlayerId)).isEqualTo(
                new GridsQuery.Grids(
                        GridsQuery.Phase.SHIP_POSITIONING,
                        firstPlayerId,
                        secondPlayerId,
                        Set.of(new GridsQuery.SquareView('a', 1, GridsQuery.Status.SHIP_SEGMENT)),
                        Set.of(),
                        LocalDateTime.of(2020, 1, 1, 0, 0, 0)
                                .toInstant(ZoneOffset.UTC)
                )
        );
    }

    @Test
    void givenShipContainsUnrelatedCells_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a3");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenNoCoordinates_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenMoreThan4Coordinates_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a2", "a3", "a4", "a5");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenShipIsNotInAStraightLine_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a2", "b2");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenDuplicateCoordinates_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a1");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenInvalidRow_whenPositionShip_thenSquareIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("z1");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates))
                .isInstanceOf(SquareIsNotValid.class);
    }

    @Test
    void givenInvalidColumn_whenPositionShip_thenSquareIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a0");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates))
                .isInstanceOf(SquareIsNotValid.class);
    }

    @Test
    void givenShipTouchesOtherShip_whenPositionShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        useCase.position(firstPlayerId, List.of("a1"));
        List<String> shipCoordinates = List.of("a2");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates))
                .isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenBoardContains4OneCellShips_whenPlaceOneCellShip_thenShipIsNotValidThrown() {
        startGame();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        useCase.position(firstPlayerId, List.of("a1"));
        useCase.position(firstPlayerId, List.of("a3"));
        useCase.position(firstPlayerId, List.of("a5"));
        useCase.position(firstPlayerId, List.of("a7"));
        List<String> shipCoordinates = List.of("a9");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates))
                .isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenBoardsAreFull_whenPositionShip_thenGameStatusIsBattle() {
        startBattle();
        PositionShipUseCase useCase = seaBattle.positionShipUseCase();
        List<String> shipCoordinates = List.of("a1");

        assertThatThrownBy(() -> useCase.position(firstPlayerId, shipCoordinates))
                .isInstanceOf(GameWasNotFound.class);
        GridsQuery.Grids firstPlayerBoards = seaBattle.boardsQuery().currentGrids(firstPlayerId);
        GridsQuery.Grids secondPlayerBoards = seaBattle.boardsQuery().currentGrids(secondPlayerId);
        assertThat(firstPlayerBoards.phase()).isEqualTo(GridsQuery.Phase.BATTLE);
        assertThat(secondPlayerBoards.phase()).isEqualTo(GridsQuery.Phase.BATTLE);
        assertThat(firstPlayerBoards.ownedSquares()).containsExactlyInAnyOrder(shipSegments());
        assertThat(firstPlayerBoards.opponentSquares()).isEmpty();
        assertThat(secondPlayerBoards.ownedSquares()).containsExactlyInAnyOrder(shipSegments());
        assertThat(secondPlayerBoards.opponentSquares()).isEmpty();
    }
}