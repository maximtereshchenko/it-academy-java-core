package by.it_academy.seabattle.application;

import by.it_academy.seabattle.port.PlayerIds;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

final class PlayerIdsInFiles extends AbstractFilesRepository implements PlayerIds {

    private final Path rootDirectory = Paths.get("players");

    PlayerIdsInFiles() {
        createDirectory(rootDirectory);
    }

    @Override
    public boolean has(UUID id) {
        return all(rootDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(UUID::fromString)
                .anyMatch(id::equals);
    }

    @Override
    public void save(UUID id) {
        write(rootDirectory.resolve(id.toString()), "");
    }
}
