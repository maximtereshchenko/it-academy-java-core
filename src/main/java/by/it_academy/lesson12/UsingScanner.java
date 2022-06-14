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
        String text = "this-is-text-with-dashes";
        Scanner scanner = new Scanner(text).useDelimiter("-");
        while (scanner.hasNext()) {
            System.out.println(scanner.next());
        }
    }

    private static void file() {
        try (Scanner scanner = new Scanner(Paths.get("src/main/resources/text.txt"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void console() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                System.out.println(line);
            }
        }
    }
}
