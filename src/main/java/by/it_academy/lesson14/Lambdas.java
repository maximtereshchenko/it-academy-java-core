package by.it_academy.lesson14;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Maxim Tereshchenko
 */
class Lambdas {

    public static void main(String[] args) {
        useAction(() -> System.out.println("Hello from lambda"));

        var text = "some text";
        Action action = () -> System.out.println(text);
        //text = "some other text";

        Function<Object, String> staticMethodReference = Objects::toString;
        var object = new Object();
        Supplier<String> methodReference = object::toString;
        Supplier<Object> constructorReference = Object::new;
    }

    private static void useAction(Action action) {
        action.doSomething();
    }
}
