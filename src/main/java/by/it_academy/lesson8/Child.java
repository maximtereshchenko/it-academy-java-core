package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class Child extends Parent {
    @Override
    void print(String first, String second) {
        System.out.println("start printing");
        super.print(first, second);
        System.out.println("end printing");
    }
}
