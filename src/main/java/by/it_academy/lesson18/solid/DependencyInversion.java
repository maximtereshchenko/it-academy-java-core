package by.it_academy.lesson18.solid;

/**
 * @author Maxim Tereshchenko
 */
class DependencyInversion {

    private interface Event {

        void trigger();
    }

    private static final class DependentButton {

        private final Lamp lamp;

        private DependentButton(Lamp lamp) {
            this.lamp = lamp;
        }

        void click() {
            lamp.turnOn();
        }
    }

    private static final class IndependentButton {

        private final Event event;


        private IndependentButton(Event event) {
            this.event = event;
        }

        void click() {
            event.trigger();
        }
    }

    private static final class LampTurnOnEvent implements Event {

        private final Lamp lamp;

        private LampTurnOnEvent(Lamp lamp) {
            this.lamp = lamp;
        }

        @Override
        public void trigger() {
            lamp.turnOn();
        }
    }

    private static final class Lamp {

        void turnOn() {

        }
    }
}
