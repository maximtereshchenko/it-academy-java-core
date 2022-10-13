package by.it_academy.lesson12;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * @author Maxim Tereshchenko
 */
class UsingFiles {

    public static void main(String[] args) throws IOException {
        Path filePath = Path.of("some file.txt");
        Path dirPath = Path.of("dir");

        System.out.println("Files.createFile(filePath) = " + Files.createFile(filePath));
        System.out.println("Files.createDirectory(dirPath) = " + Files.createDirectory(dirPath));
        System.out.println("Files.move(filePath, dirPath.resolve(\"some new file.txt\")) = " + Files.move(filePath, dirPath.resolve("some new file.txt")));

        try (Stream<Path> stream = Files.list(dirPath)) {
            stream.forEach(path -> System.out.println("in directory path.getFileName() = " + path.getFileName()));
        }

        System.out.println("Files.deleteIfExists(filePath) = " + Files.deleteIfExists(filePath));

        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("delete file.getFileName() = " + file.getFileName());
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(dirPath, fileVisitor);

        System.out.println("Files.deleteIfExists(dirPath) = " + Files.deleteIfExists(dirPath));
    }
}
