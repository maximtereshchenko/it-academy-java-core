package by.it_academy.lesson18.greeters;

/**
 * @author Maxim Tereshchenko
 */
public class SimpleGreeter implements Greeter {

    @Override
    public String greet(String name) {
        return "Hello, " + name;
    }
}
