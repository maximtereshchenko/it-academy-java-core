package by.it_academy.lesson18.patterns;

import java.util.Collection;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
interface Subscriber {
    void consume(String message);
}

class First implements Subscriber {

    @Override
    public void consume(String message) {
        System.out.println("First " + message);
    }
}

class Second implements Subscriber {

    @Override
    public void consume(String message) {
        System.out.println("Second " + message);
    }
}

class Publisher {

    private final Collection<Subscriber> subscribers;

    Publisher(Subscriber... subscribers) {
        this.subscribers = List.of(subscribers);
    }

    void send(String message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.consume(message);
        }
    }
}

class Component {

    private final Publisher publisher;
    private String state;

    Component(Publisher publisher) {
        this.publisher = publisher;
    }

    void changeState(String state) {
        this.state = state;
        publisher.send(state);
    }
}

class Observer {

    public static void main(String[] args) {
        var component = new Component(new Publisher(new First(), new Second()));

        component.changeState("1");
        component.changeState("2");
    }
}
