package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class Overloading {

    public static void main(String[] args) {
        var parent = new Parent();

        parent.print("house", "car");
        parent.print("station");
    }
}
