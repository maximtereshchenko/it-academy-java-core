package by.it_academy.seabattle.port;

import java.util.UUID;

public interface PlayerIds {

    boolean exists(UUID id);

    void save(UUID id);
}
