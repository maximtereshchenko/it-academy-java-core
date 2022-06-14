package by.it_academy.lesson18.patterns;

import by.it_academy.lesson18.FileReader;
import by.it_academy.lesson18.ScannerAdapter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Maxim Tereshchenko
 */
class Adapter {

    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(Paths.get("file"))) {
            read(new ScannerAdapter(scanner));
        }
    }

    public static void read(FileReader fileReader) {
        String file = fileReader.read("file");
    }
}
