package by.it_academy.lesson18.solid;

/**
 * @author Maxim Tereshchenko
 */
class SingleResponsibility {

    private static final class Manager {

        private final CoffeeMachine coffeeMachine = new CoffeeMachine();
        private final Computer computer = new Computer();

        void makeCoffee() {
            coffeeMachine.prepareCoffee();
        }

        String annualReport() {
            return computer.createSpreadsheets();
        }
    }


    private static class CoffeeMachine {
        void prepareCoffee() {

        }
    }

    private static class Computer {
        String createSpreadsheets() {
            return null;
        }
    }
}
