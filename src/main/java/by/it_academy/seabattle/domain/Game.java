package by.it_academy.seabattle.domain;

import by.it_academy.seabattle.port.GameStates;
import by.it_academy.seabattle.usecase.BoardsQuery;

import java.util.UUID;

interface Game {

    UUID id();

    Game placeShip(Player player, Ship ship);

    Game shoot(Player player, Cell cell);

    GameStates.State state();

    BoardsQuery.Boards view(Player player);
}
