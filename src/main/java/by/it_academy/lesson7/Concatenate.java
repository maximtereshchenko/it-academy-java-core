package by.it_academy.lesson7;

/**
 * @author Maxim Tereshchenko
 */
class Concatenate {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            var start = System.currentTimeMillis();
            concat();
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    private static String concat() {
        var result = "";

        for (int i = 0; i < 100000; i++) {
            result += "a";
        }

        return result;
    }
}
