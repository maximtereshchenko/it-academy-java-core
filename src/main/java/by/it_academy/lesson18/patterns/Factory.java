package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
class Factory {

    Colour colour(String colour) {
        switch (colour) {
            case "red":
                return new Red();
            case "green":
                return new Green();
            case "blue":
                return new Blue();
            default:
                return null;
        }
    }

    interface Colour {}

    static final class Red implements Colour {}

    static final class Green implements Colour {}

    static final class Blue implements Colour {}
}
