package by.it_academy.lesson18.greeters;

/**
 * @author Maxim Tereshchenko
 */
class BadGreater {

    String greet1(String name) {
        return "Hello, " + name;
    }

    String greet2(String name) {
        return "Hello, " + name + "!";
    }

    String greet3(String name, boolean polite) {
        var fullName = polite ? "Mr. " + name : name;
        return "Hello, " + fullName + "!";
    }
}
