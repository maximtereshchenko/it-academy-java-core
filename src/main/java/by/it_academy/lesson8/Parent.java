package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class Parent {

    void print(String first, String second) {
        System.out.println("First is " + first + " and second is " + second);
    }

    void print(String str) {
        print(str, str);
    }
}
