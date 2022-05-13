package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface Builder {

    void rooms(int number);

    void toilets(int number);

    void kitchen();

    void garage();

    void reset();

    House build();
}

interface House {}

class Foreman {

    private final Builder builder;

    Foreman(Builder builder) {
        this.builder = builder;
    }

    House small() {
        builder.reset();
        builder.rooms(2);
        builder.toilets(1);
        builder.kitchen();
        return builder.build();
    }

    House big() {
        builder.reset();
        builder.rooms(5);
        builder.toilets(2);
        builder.kitchen();
        builder.garage();
        return builder.build();
    }
}
