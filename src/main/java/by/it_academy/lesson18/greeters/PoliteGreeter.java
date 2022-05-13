package by.it_academy.lesson18.greeters;

/**
 * @author Maxim Tereshchenko
 */
public class PoliteGreeter implements Greeter {

    private final Greeter original;

    public PoliteGreeter(Greeter original) {
        this.original = original;
    }

    @Override
    public String greet(String name) {
        return original.greet("Mr. " + name);
    }
}
