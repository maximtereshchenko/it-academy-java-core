package by.it_academy.lesson18.greeters;

/**
 * @author Maxim Tereshchenko
 */
public class ExitingGreeter implements Greeter {

    private final Greeter original;

    public ExitingGreeter(Greeter original) {
        this.original = original;
    }

    @Override
    public String greet(String name) {
        return original.greet(name) + "!";
    }
}
