package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.ui.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

final class Runner {

    public static void main(String[] args) throws TelegramApiException {
        SeaBattle seaBattle = new SeaBattle(new InMemoryPlayerIds(), new InMemoryGameStates());
        List<Command> mainCommands = List.of(
                new FillGridCommand(seaBattle.fillGridWithRandomShipsUseCase()),
                new GridsCommand(seaBattle.boardsQuery()),
                new PositionCommand(seaBattle.positionShipUseCase()),
                new QueueCommand(seaBattle.addPlayerToQueueUseCase()),
                new RegisterCommand(seaBattle.registerNewPlayerUseCase()),
                new ShootCommand(seaBattle.shootUseCase())
        );
        TextInterface textInterface = new TextInterface(concat(mainCommands, new HelpCommand(mainCommands)));
        new TelegramBotsApi(DefaultBotSession.class)
                .registerBot(
                        new SeaBattleTelegramBot(
                                new InMemoryChats(),
                                textInterface,
                                seaBattle.addGameStartedObserverUseCase()
                        )
                );
        Executors.newSingleThreadExecutor()
                .execute(new ConsoleInterface(textInterface, seaBattle.addGameStartedObserverUseCase()));
    }

    private static List<Command> concat(Collection<Command> commands, Command other) {
        List<Command> list = new ArrayList<>(commands);
        list.add(other);
        return list;
    }
}
