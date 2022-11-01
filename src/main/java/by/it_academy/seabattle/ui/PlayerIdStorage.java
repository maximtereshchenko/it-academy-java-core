package by.it_academy.seabattle.ui;

import java.util.Optional;
import java.util.UUID;

public interface PlayerIdStorage {

    Optional<UUID> get();

    void set(UUID id);
}
