package by.it_academy.lesson7;

/**
 * @author Maxim Tereshchenko
 */
class Buffer {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            buffer();
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    private static String buffer() {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < 100000; i++) {
            buffer.append("a");
        }

        return buffer.toString();
    }
}
