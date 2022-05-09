package by.it_academy.lesson6;

/**
 * @author Maxim Tereshchenko
 */
abstract class AbstractWorker implements Worker {

    private final String name;

    AbstractWorker(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
