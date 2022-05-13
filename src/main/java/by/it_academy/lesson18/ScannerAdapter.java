package by.it_academy.lesson18;

import java.util.Scanner;

/**
 * @author Maxim Tereshchenko
 */
public class ScannerAdapter implements FileReader {

    private final Scanner scanner;

    public ScannerAdapter(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public String read(String fileName) {
        var builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine())
                    .append(System.lineSeparator());
        }

        return builder.toString();
    }
}
