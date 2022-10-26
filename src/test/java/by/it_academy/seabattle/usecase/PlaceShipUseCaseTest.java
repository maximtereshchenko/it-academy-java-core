package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerStates;
import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.usecase.exception.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlaceShipUseCaseTest {

    private final SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerStates(), new InMemoryGameStates());
    private final UUID firstPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private final UUID secondPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000002");

    @Test
    void givenNonexistentPlayerId_whenPlaceShip_thenPlayerWasNotFoundThrown() {
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        Collection<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(PlayerWasNotFound.class);
    }

    @Test
    void givenNoGameWithPlayerId_whenPlaceShip_thenGameWasNotFoundThrown() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        Collection<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(GameWasNotFound.class);
    }

    @Test
    void givenGameExists_whenPlaceShip_thenShipPlaced() {
        startGame();

        seaBattle.placeShipUseCase().place(firstPlayerId, List.of("a1"));

        assertThat(seaBattle.boardsQuery().getCurrentBoards(firstPlayerId)).isEqualTo(
                new BoardsQuery.Boards(
                        BoardsQuery.Phase.SHIP_PLACEMENT,
                        firstPlayerId,
                        secondPlayerId,
                        Set.of(new BoardsQuery.CellView('a', 1, BoardsQuery.Status.SHIP_SEGMENT)),
                        Set.of()
                )
        );
    }

    @Test
    void givenShipContainsUnrelatedCells_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a3");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenNoCoordinates_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of();

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenMoreThan4Coordinates_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a2", "a3", "a4", "a5");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenShipIsNotInAStraightLine_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a2", "b2");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenDuplicateCoordinates_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a1", "a1");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates)).isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenInvalidRow_whenPlaceShip_thenCoordinatesIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("z1");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates))
                .isInstanceOf(CoordinatesIsNotValid.class);
    }

    @Test
    void givenInvalidColumn_whenPlaceShip_thenCoordinatesIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a0");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates))
                .isInstanceOf(CoordinatesIsNotValid.class);
    }

    @Test
    void givenShipTouchesOtherShip_whenPlaceShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        useCase.place(firstPlayerId, List.of("a1"));
        List<String> shipCoordinates = List.of("a2");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates))
                .isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenBoardContains4OneCellShips_whenPlaceOneCellShip_thenShipIsNotValidThrown() {
        startGame();
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        useCase.place(firstPlayerId, List.of("a1"));
        useCase.place(firstPlayerId, List.of("a3"));
        useCase.place(firstPlayerId, List.of("a5"));
        useCase.place(firstPlayerId, List.of("a7"));
        List<String> shipCoordinates = List.of("a9");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates))
                .isInstanceOf(ShipIsNotValid.class);
    }

    @Test
    void givenBoardsAreFull_whenPlaceShip_thenGameStatusIsBattle() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
        PlaceShipUseCase useCase = seaBattle.placeShipUseCase();
        List<String> shipCoordinates = List.of("a1");

        assertThatThrownBy(() -> useCase.place(firstPlayerId, shipCoordinates))
                .isInstanceOf(BattleHasBeenStarted.class);
        BoardsQuery.Boards firstPlayerBoards = seaBattle.boardsQuery().getCurrentBoards(firstPlayerId);
        BoardsQuery.Boards secondPlayerBoards = seaBattle.boardsQuery().getCurrentBoards(secondPlayerId);
        assertThat(firstPlayerBoards.phase()).isEqualTo(BoardsQuery.Phase.BATTLE);
        assertThat(secondPlayerBoards.phase()).isEqualTo(BoardsQuery.Phase.BATTLE);
        assertThat(firstPlayerBoards.ownedCells()).containsExactlyInAnyOrder(expectedCells());
        assertThat(firstPlayerBoards.opponentCells()).isEmpty();
        assertThat(secondPlayerBoards.ownedCells()).containsExactlyInAnyOrder(expectedCells());
        assertThat(secondPlayerBoards.opponentCells()).isEmpty();
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

    private BoardsQuery.CellView[] expectedCells() {
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