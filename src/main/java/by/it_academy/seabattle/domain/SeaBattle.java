package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.port.PlayerIds;
import by.it_academy.seabattle.usecase.*;

public class SeaBattle {

    private final PlayerService playerService;
    private final QueueService queueService;
    private final GameService gameService;
    private final PlayerShotObservable playerShotObservable;
    private final GameOverObservable gameOverObservable;
    private final AllShipsPositionedObservable allShipsPositionedObservable;

    public SeaBattle(PlayerIds playerIds, GameStates gameStates) {
        Players players = new Players(playerIds);
        ShipFactory shipFactory = new ShipFactory();
        Games games = new Games(gameStates, shipFactory);
        playerService = new PlayerService(players);
        queueService = new QueueService(players, games, new Queue(games));
        gameService = new GameService(players, games, shipFactory);
        playerShotObservable = new PlayerShotObservable(gameService, players, games);
        gameOverObservable = new GameOverObservable(playerShotObservable, players, games);
        allShipsPositionedObservable = new AllShipsPositionedObservable(gameService, gameService, players, games);
    }

    public RegisterNewPlayerUseCase registerNewPlayerUseCase() {
        return playerService;
    }

    public AddPlayerToQueueUseCase addPlayerToQueueUseCase() {
        return queueService;
    }

    public PositionShipUseCase positionShipUseCase() {
        return allShipsPositionedObservable;
    }

    public GridsQuery boardsQuery() {
        return gameService;
    }

    public ShootUseCase shootUseCase() {
        return gameOverObservable;
    }

    public FillGridWithRandomShipsUseCase fillGridWithRandomShipsUseCase() {
        return allShipsPositionedObservable;
    }

    public AddGameStartedObserverUseCase addGameStartedObserverUseCase() {
        return queueService;
    }

    public AddPlayerShotObserverUseCase addPlayerShotObserverUseCase() {
        return playerShotObservable;
    }

    public AddGameOverObserverUseCase addGameOverObserverUseCase() {
        return gameOverObservable;
    }

    public AddAllShipsPositionedObserverUseCase addAllShipsPositionedObserverUseCase() {
        return allShipsPositionedObservable;
    }
}
