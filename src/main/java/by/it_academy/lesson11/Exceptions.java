package by.it_academy.lesson11;

import java.io.IOException;

/**
 * @author Maxim Tereshchenko
 */
class Exceptions {

    public static void main(String[] args) {
        safeMethod();
        try {
            throwsUncheckedException();
            throwsCheckedException();
        } catch (CheckedException e) {
            System.out.println("Caught checked");
        } catch (UncheckedException e) {
            System.out.println("Caught unchecked");
        }

        try (Resource resource = new Resource()) {
            resource.doSomething();
        } catch (UncheckedException e) {
            System.out.println("Resource threw exception");
        } catch (IOException e) {
            System.out.println("Could not create resource");
        }
    }

    private static void throwsCheckedException() throws CheckedException {
        throw new CheckedException();
    }

    private static void throwsUncheckedException() {
        throw new UncheckedException();
    }

    private static void safeMethod() {
    }
}
