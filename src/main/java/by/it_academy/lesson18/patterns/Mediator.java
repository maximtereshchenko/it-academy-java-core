package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface Mediator {

    void notify(Object sender, String event);
}

class Button {

    private final Mediator mediator;

    Button(Mediator mediator) {
        this.mediator = mediator;
    }

    void click() {
        mediator.notify(this, "click");
    }

    void hold() {
        mediator.notify(this, "hold");
    }
}

class Lamp {

    private boolean turnedOn = false;

    boolean isTurnedOn() {
        return turnedOn;
    }

    void turnOn() {
        turnedOn = true;
    }

    void turnOff() {
        turnedOn = false;
    }
}

class SwitchLampOnClick implements Mediator {

    private Button button;
    private Lamp lamp;

    @Override
    public void notify(Object sender, String event) {
        if (sender == button && event.equals("click")) {
            if (lamp.isTurnedOn()) {
                lamp.turnOff();
            } else {
                lamp.turnOn();
            }
        }
    }

    void setButton(Button button) {
        this.button = button;
    }

    void setLamp(Lamp lamp) {
        this.lamp = lamp;
    }
}

class TurnOnClickTurnOffHold implements Mediator {

    private Button button;
    private Lamp lamp;

    @Override
    public void notify(Object sender, String event) {
        if (sender == button && event.equals("click")) {
            lamp.turnOn();
        }
        if (sender == button && event.equals("hold")) {
            lamp.turnOff();
        }
    }

    void setButton(Button button) {
        this.button = button;
    }

    void setLamp(Lamp lamp) {
        this.lamp = lamp;
    }
}

class MediatorExample {

    public static void main(String[] args) {
        var switchLampOnClick = new SwitchLampOnClick();
        var firstButton = new Button(switchLampOnClick);
        var lamp = new Lamp();
        switchLampOnClick.setButton(firstButton);
        switchLampOnClick.setLamp(lamp);

        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        firstButton.click();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        firstButton.click();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        firstButton.hold();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        System.out.println();

        var turnOnClickTurnOffHold = new TurnOnClickTurnOffHold();
        var secondButton = new Button(turnOnClickTurnOffHold);
        turnOnClickTurnOffHold.setButton(secondButton);
        turnOnClickTurnOffHold.setLamp(lamp);

        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        secondButton.click();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        secondButton.click();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
        secondButton.hold();
        System.out.println("lamp.isTurnedOn() = " + lamp.isTurnedOn());
    }
}
