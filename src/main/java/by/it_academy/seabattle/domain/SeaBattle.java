package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.port.PlayerIds;
import by.it_academy.seabattle.usecase.*;

public class SeaBattle {

    private final PlayerService playerService;
    private final QueueService queueService;
    private final GameService gameService;
    private final AddPlayerShotObserverUseCase addPlayerShotObserverUseCase;
    private final AddGameOverObserverUseCase addGameOverObserverUseCase;
    private final ShootUseCase shootUseCase;

    public SeaBattle(PlayerIds playerIds, GameStates gameStates) {
        Players players = new Players(playerIds);
        ShipFactory shipFactory = new ShipFactory();
        Games games = new Games(gameStates, shipFactory);
        playerService = new PlayerService(players);
        queueService = new QueueService(players, games, new Queue(games));
        gameService = new GameService(players, games, shipFactory);
        PlayerShotObservable playerShotObservable = new PlayerShotObservable(gameService, players, games);
        GameOverObservable gameOverObservable = new GameOverObservable(playerShotObservable, players, games);
        addPlayerShotObserverUseCase = playerShotObservable;
        addGameOverObserverUseCase = gameOverObservable;
        shootUseCase = gameOverObservable;
    }

    public RegisterNewPlayerUseCase registerNewPlayerUseCase() {
        return playerService;
    }

    public AddPlayerToQueueUseCase addPlayerToQueueUseCase() {
        return queueService;
    }

    public PositionShipUseCase positionShipUseCase() {
        return gameService;
    }

    public GridsQuery boardsQuery() {
        return gameService;
    }

    public ShootUseCase shootUseCase() {
        return shootUseCase;
    }

    public FillGridWithRandomShipsUseCase fillGridWithRandomShipsUseCase() {
        return gameService;
    }

    public AddGameStartedObserverUseCase addGameStartedObserverUseCase() {
        return queueService;
    }

    public AddPlayerShotObserverUseCase addPlayerShotObserverUseCase() {
        return addPlayerShotObserverUseCase;
    }

    public AddGameOverObserverUseCase addGameOverObserverUseCase() {
        return addGameOverObserverUseCase;
    }
}
