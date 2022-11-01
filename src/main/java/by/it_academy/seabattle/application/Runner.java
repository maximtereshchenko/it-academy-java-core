package by.it_academy.seabattle.application;

import by.it_academy.seabattle.domain.SeaBattle;
import by.it_academy.seabattle.ui.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

final class Runner {

    public static void main(String[] args) throws TelegramApiException {
        SeaBattle seaBattle = new SeaBattle(
                new PlayerIdsInFiles(),
                new GameStatesInFiles(),
                Clock.systemDefaultZone()
        );
        GridsCommand gridsCommand = new GridsCommand(seaBattle.boardsQuery());
        new TelegramBotsApi(DefaultBotSession.class)
                .registerBot(
                        new SeaBattleTelegramBot(
                                new ChatsInFiles(),
                                new TextInterface(commands(seaBattle, new UseMonospaceFont(gridsCommand))),
                                seaBattle
                        )
                );
        Executors.newSingleThreadExecutor()
                .execute(new ConsoleInterface(new TextInterface(commands(seaBattle, gridsCommand)), seaBattle));
    }

    private static Collection<Command> commands(SeaBattle seaBattle, Command gridsCommand) {
        Collection<Command> mainCommands = concat(mainCommands(seaBattle), gridsCommand);
        return concat(mainCommands, new HelpCommand(mainCommands));
    }

    private static Collection<Command> mainCommands(SeaBattle seaBattle) {
        return List.of(
                new FillGridCommand(seaBattle.fillGridWithRandomShipsUseCase()),
                new PositionCommand(seaBattle.positionShipUseCase()),
                new QueueCommand(seaBattle.addPlayerToQueueUseCase()),
                new PlayerCommand(seaBattle.registerNewPlayerUseCase()),
                new ShootCommand(seaBattle.shootUseCase(), seaBattle.squareQuery())
        );
    }

    private static List<Command> concat(Collection<Command> commands, Command other) {
        List<Command> list = new ArrayList<>(commands);
        list.add(other);
        return list;
    }
}
