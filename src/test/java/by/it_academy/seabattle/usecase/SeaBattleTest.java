package by.it_academy.seabattle.usecase;

import by.it_academy.seabattle.application.InMemoryGameStates;
import by.it_academy.seabattle.application.InMemoryPlayerIds;
import by.it_academy.seabattle.domain.SeaBattle;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

abstract class SeaBattleTest {

    final SeaBattle seaBattle = new SeaBattle(
            new InMemoryPlayerIds(),
            new InMemoryGameStates(),
            Clock.fixed(Instant.parse("2020-01-01T00:00:00Z"), ZoneOffset.UTC)
    );
    final UUID firstPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000001");
    final UUID secondPlayerId = UUID.fromString("00000000-0000-0000-0000-000000000002");

    void placeAllShips(UUID playerId) {
        ships().forEach(ship -> seaBattle.positionShipUseCase().position(playerId, ship));
    }

    void startGame() {
        seaBattle.registerNewPlayerUseCase().register(firstPlayerId);
        seaBattle.registerNewPlayerUseCase().register(secondPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(firstPlayerId);
        seaBattle.addPlayerToQueueUseCase().add(secondPlayerId);
    }

    void startBattle() {
        startGame();
        placeAllShips(firstPlayerId);
        placeAllShips(secondPlayerId);
    }

    void destroyAllEnemyShips() {
        ships()
                .stream()
                .flatMap(Collection::stream)
                .forEach(square -> seaBattle.shootUseCase().shoot(firstPlayerId, square));
    }

    GridsQuery.SquareView[] shipSegments() {
        return ships()
                .stream()
                .flatMap(Collection::stream)
                .map(square ->
                        new GridsQuery.SquareView(
                                square.charAt(0),
                                Character.digit(square.charAt(1), 10),
                                GridsQuery.Status.SHIP_SEGMENT
                        )
                )
                .toArray(GridsQuery.SquareView[]::new);
    }

    Collection<Collection<String>> ships() {
        return List.of(
                List.of("a1"),
                List.of("a3"),
                List.of("a5"),
                List.of("a7"),
                List.of("c1", "d1"),
                List.of("c3", "d3"),
                List.of("c5", "d5"),
                List.of("f1", "g1", "h1"),
                List.of("f3", "g3", "h3"),
                List.of("f5", "g5", "h5", "i5")
        );
    }
}
