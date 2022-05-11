package by.it_academy.lesson12;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Maxim Tereshchenko
 */
class UsingScanner {

    public static void main(String[] args) {
        file();
        delimiter();

        console();
    }

    private static void delimiter() {
        var text = "this-is-text-with-dashes";
        var scanner = new Scanner(text).useDelimiter("-");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    private static void file() {
        try (var scanner = new Scanner(Paths.get("src/main/java/by/it_academy/lesson12/text.txt"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void console() {
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                var line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }
        }
    }
}
