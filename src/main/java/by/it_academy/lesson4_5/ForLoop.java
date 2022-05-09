package by.it_academy.lesson4_5;

/**
 * @author Maxim Tereshchenko
 */
class ForLoop {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
        }

        for (int i = 10; i > 0; i--) {
            System.out.println(i);
        }

        for (int i = 0, j = 10; i < 10 && j > 0; i++, j--) {
            System.out.println("" + i + " and " + j);
        }

        for (; ; ) {
            System.out.println("endless");
        }
    }
}
