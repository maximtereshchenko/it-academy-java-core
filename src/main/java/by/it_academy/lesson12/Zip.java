package by.it_academy.lesson12;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Maxim Tereshchenko
 */
class Zip {

    public static void main(String[] args) {
        createArchive();
        readArchive();
    }

    private static void readArchive() {
        try (var stream = new ZipInputStream(new BufferedInputStream(new FileInputStream("archive.zip")))) {
            var entry = stream.getNextEntry();
            while (entry != null) {
                System.out.println(entry.getName());
                var buffer = new byte[1000];
                var read = -1;
                do {
                    read = stream.read(buffer);
                    for (int i = 0; i < read; i++) {
                        System.out.print((char) buffer[i]);
                    }
                } while (read != -1);
                System.out.println();
                entry = stream.getNextEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createArchive() {
        try (var stream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("archive.zip")))) {
            stream.putNextEntry(new ZipEntry("file.txt"));
            stream.write("Hello, world!".getBytes(StandardCharsets.UTF_8));
            stream.closeEntry();
            stream.putNextEntry(new ZipEntry("file2.txt"));
            stream.write("Hello, world!2".getBytes(StandardCharsets.UTF_8));
            stream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
