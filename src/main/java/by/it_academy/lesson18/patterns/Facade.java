package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
class FuelInjector {
    void on() {
    }

    void inject() {
    }

    void off() {
    }
}

class AirFlowController {
    void takeAir() {
    }

    void off() {
    }
}

class Starter {
    void start() {
    }
}

class CoolingController {
    void setTemperatureUpperLimit(int temp) {
    }

    void run() {
    }

    void cool(int temp) {
    }

    void stop() {
    }
}

class CatalyticConverter {
    void on() {
    }

    void off() {
    }
}

class Facade {

    private final static int DEFAULT_COOLING_TEMP = 90;
    private final static int MAX_ALLOWED_TEMP = 50;
    private final Starter starter = new Starter();
    private final FuelInjector fuelInjector = new FuelInjector();
    private final AirFlowController airFlowController = new AirFlowController();
    private final CoolingController coolingController = new CoolingController();
    private final CatalyticConverter catalyticConverter = new CatalyticConverter();

    void startEngine() {
        fuelInjector.on();
        airFlowController.takeAir();
        fuelInjector.on();
        fuelInjector.inject();
        starter.start();
        coolingController.setTemperatureUpperLimit(DEFAULT_COOLING_TEMP);
        coolingController.run();
        catalyticConverter.on();
    }

    void stopEngine() {
        fuelInjector.off();
        catalyticConverter.off();
        coolingController.cool(MAX_ALLOWED_TEMP);
        coolingController.stop();
        airFlowController.off();
    }
}
