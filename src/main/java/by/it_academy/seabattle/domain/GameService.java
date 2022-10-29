package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.usecase.FillGridWithRandomShipsUseCase;
import by.it_academy.seabattle.usecase.GridsQuery;
import by.it_academy.seabattle.usecase.PositionShipUseCase;
import by.it_academy.seabattle.usecase.ShootUseCase;

import java.util.Collection;
import java.util.UUID;

final class GameService implements PositionShipUseCase, GridsQuery, FillGridWithRandomShipsUseCase, ShootUseCase {

    private final Players players;
    private final Games games;
    private final ShipFactory shipFactory;

    GameService(Players players, Games games, ShipFactory shipFactory) {
        this.players = players;
        this.games = games;
        this.shipFactory = shipFactory;
    }

    @Override
    public void position(UUID playerId, Collection<String> shipCoordinates) {
        Player player = players.findPlayer(playerId);
        games.save(
                games.findGameInShipPositioningPhaseByPlayer(player)
                        .position(player, shipFactory.intactShip(shipCoordinates))
        );
    }

    @Override
    public Grids currentGrids(UUID playerId) {
        return games.findGameByPlayer(players.findPlayer(playerId)).view(players.findPlayer(playerId));
    }

    @Override
    public void fill(UUID playerId) {
        Player player = players.findPlayer(playerId);
        games.save(games.findGameInShipPositioningPhaseByPlayer(player).fill(player, shipFactory));
    }

    @Override
    public void shoot(UUID playerId, String coordinates) {
        Player player = players.findPlayer(playerId);
        games.save(games.findGameInBattlePhaseByPlayer(player).shoot(player, Square.of(coordinates)));
    }
}
