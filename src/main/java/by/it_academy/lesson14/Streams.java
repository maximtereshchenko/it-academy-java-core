package by.it_academy.lesson14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
class Streams {

    public static void main(String[] args) {
        Collection<String> cities = cities();

        List<String> list = cities.stream()
                .filter(city -> city.startsWith("A"))
                .filter(city -> city.length() <= 5)
                .map(String::toUpperCase)
                .sorted()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(list);
    }

    private static Collection<String> cities() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/cities.txt"))) {
            return reader.lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
