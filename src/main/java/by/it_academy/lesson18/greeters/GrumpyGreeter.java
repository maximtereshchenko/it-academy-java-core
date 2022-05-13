package by.it_academy.lesson18.greeters;

/**
 * @author Maxim Tereshchenko
 */
public class GrumpyGreeter implements Greeter {

    @Override
    public String greet(String name) {
        throw new RuntimeException("I do'nt wanna greet...");
    }
}
