package by.it_academy.seabattle.application;

import java.util.UUID;

interface Chats {

    UUID playerId(long chatId);

    void save(long chatId, UUID playerId);
}
