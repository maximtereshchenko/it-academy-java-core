package by.it_academy.seabattle.ui;

import java.util.UUID;

public interface PlayerIdStorage {

    UUID get();

    void set(UUID id);
}
