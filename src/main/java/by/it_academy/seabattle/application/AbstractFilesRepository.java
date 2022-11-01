package by.it_academy.seabattle.application;

import by.it_academy.seabattle.usecase.exception.UnexpectedException;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

abstract class AbstractFilesRepository {

    void createDirectory(Path path) {
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {
            //ignored
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }

    Stream<Path> all(Path directory) {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }

    String read(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }

    void write(Path path, String content) {
        try {
            Files.writeString(path, content);
        } catch (IOException e) {
            throw new UnexpectedException(e);
        }
    }
}
