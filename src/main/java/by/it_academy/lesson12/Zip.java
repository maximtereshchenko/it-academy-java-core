package by.it_academy.lesson12;

import java.io.*;
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
        try (ZipInputStream stream = new ZipInputStream(new BufferedInputStream(new FileInputStream("archive.zip")))) {
            ZipEntry entry = stream.getNextEntry();
            while (entry != null) {
                System.out.println(entry.getName());
                byte[] buffer = new byte[1000];
                int read;
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
        try (ZipOutputStream stream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("archive.zip")))) {
            stream.putNextEntry(new ZipEntry("file.txt"));
            stream.write("Hello, world!".getBytes());
            stream.closeEntry();
            stream.putNextEntry(new ZipEntry("file2.txt"));
            stream.write("Hello, world!2".getBytes());
            stream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
