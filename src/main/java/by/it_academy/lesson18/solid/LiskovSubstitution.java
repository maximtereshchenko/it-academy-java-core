package by.it_academy.lesson18.solid;

import by.it_academy.lesson18.greeters.Greeter;
import by.it_academy.lesson18.greeters.GrumpyGreeter;
import by.it_academy.lesson18.greeters.SimpleGreeter;

/**
 * @author Maxim Tereshchenko
 */
class LiskovSubstitution {

    public static void main(String[] args) {
        greet(new SimpleGreeter());
        greet(new GrumpyGreeter());
    }

    private static void greet(Greeter greeter) {
        greeter.greet("Maxim");
    }
}
