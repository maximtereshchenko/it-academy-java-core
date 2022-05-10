package by.it_academy.lesson11;

import java.io.IOException;

/**
 * @author Maxim Tereshchenko
 */
class Resource implements AutoCloseable {

    Resource() throws IOException {
        System.out.println("Created");
    }

    @Override
    public void close() {
        System.out.println("Closing");
    }

    void doSomething() {
        throw new UncheckedException();
    }
}
