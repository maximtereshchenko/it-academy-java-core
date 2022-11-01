package by.it_academy.seabattle.usecase.exception;

public final class UnexpectedException extends RuntimeException {

    public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Throwable cause) {
        super(cause);
    }
}
