package by.it_academy.seabattle.complete.port;

import java.util.UUID;

public interface PlayerIds {

    boolean has(UUID id);

    void save(UUID id);
}
