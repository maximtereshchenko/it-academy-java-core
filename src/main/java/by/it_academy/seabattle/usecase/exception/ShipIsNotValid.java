package by.it_academy.seabattle.usecase.exception;

public class ShipIsNotValid extends RuntimeException {

    public ShipIsNotValid() {
    }

    public ShipIsNotValid(String message) {
        super(message);
    }
}
