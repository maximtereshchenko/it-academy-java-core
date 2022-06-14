package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class Overriding {

    public static void main(String[] args) {
        Child child = new Child();

        child.print("house", "car");
        child.print("station");
    }
}
