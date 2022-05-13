package by.it_academy.lesson18.solid;

import by.it_academy.lesson18.greeters.ExitingGreeter;
import by.it_academy.lesson18.greeters.Greeter;
import by.it_academy.lesson18.greeters.PoliteGreeter;
import by.it_academy.lesson18.greeters.SimpleGreeter;

/**
 * @author Maxim Tereshchenko
 */
class OpenClosed {

    public static void main(String[] args) {
        Greeter greeter = new SimpleGreeter();
        System.out.println(greeter.greet("Maxim"));

        greeter = new ExitingGreeter(greeter);
        System.out.println(greeter.greet("Maxim"));

        greeter = new PoliteGreeter(greeter);
        System.out.println(greeter.greet("Maxim"));
    }
}
