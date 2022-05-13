package by.it_academy.lesson18.patterns;

import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
class Iterator {

    public static void main(String[] args) {
        var strings = List.of("1", "2");

        for (String string : strings) { //using iterator
            System.out.println(string);
        }
    }
}
