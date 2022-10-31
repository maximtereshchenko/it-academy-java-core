package by.it_academy.seabattle.application;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

final class ChatsInFiles extends AbstractFilesRepository implements Chats {

    private final Path rootDirectory = Paths.get("chats");

    ChatsInFiles() {
        createDirectory(rootDirectory);
    }

    @Override
    public Optional<UUID> playerId(long chatId) {
        return all(rootDirectory)
                .filter(path -> path.getFileName().toString().equals(String.valueOf(chatId)))
                .map(this::read)
                .map(UUID::fromString)
                .findAny();
    }

    @Override
    public Optional<Long> chatId(UUID playerId) {
        return all(rootDirectory)
                .filter(path -> UUID.fromString(read(path)).equals(playerId))
                .map(Path::getFileName)
                .map(Path::toString)
                .map(Long::valueOf)
                .findAny();
    }

    @Override
    public void save(long chatId, UUID playerId) {
        write(rootDirectory.resolve(String.valueOf(chatId)), playerId.toString());
    }
}
