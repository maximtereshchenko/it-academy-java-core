package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.BoardsQuery;
import by.it_academy.seabattle.usecase.FillBoardWithRandomShipsUseCase;
import by.it_academy.seabattle.usecase.PlaceShipUseCase;
import by.it_academy.seabattle.usecase.ShootUseCase;
import by.it_academy.seabattle.usecase.exception.GameWasNotFound;
import by.it_academy.seabattle.usecase.exception.PlayerWasNotFound;

import java.util.Collection;
import java.util.UUID;

class GameService implements PlaceShipUseCase, BoardsQuery, ShootUseCase, FillBoardWithRandomShipsUseCase {

    private final Players players;
    private final Games games;
    private final ShipFactory shipFactory;

    GameService(Players players, Games games, ShipFactory shipFactory) {
        this.players = players;
        this.games = games;
        this.shipFactory = shipFactory;
    }

    @Override
    public void place(UUID playerId, Collection<String> shipCoordinates) {
        Player player = players.findById(playerId).orElseThrow(PlayerWasNotFound::new);
        games.save(
                games.findByPlayer(player)
                        .orElseThrow(GameWasNotFound::new)
                        .placeShip(player, shipFactory.ship(shipCoordinates))
        );
    }

    @Override
    public BoardsQuery.Boards getCurrentBoards(UUID playerId) {
        Player player = players.findById(playerId).orElseThrow(PlayerWasNotFound::new);
        return games.findByPlayer(player)
                .orElseThrow(GameWasNotFound::new)
                .view(player);
    }

    @Override
    public void shoot(UUID playerId, String coordinates) {
        Player player = players.findById(playerId).orElseThrow(PlayerWasNotFound::new);
        games.save(
                games.findByPlayer(player).orElseThrow(GameWasNotFound::new)
                        .shoot(player, Cell.of(coordinates))
        );
    }

    @Override
    public void fill(UUID playerId) throws PlayerWasNotFound, GameWasNotFound {

    }
}
