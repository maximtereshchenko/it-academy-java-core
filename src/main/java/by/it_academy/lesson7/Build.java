package by.it_academy.lesson7;

/**
 * @author Maxim Tereshchenko
 */
class Build {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            build();
            System.out.println(System.currentTimeMillis() - start);
        }
    }

    private static String build() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 100000; i++) {
            builder.append("a");
        }

        return builder.toString();
    }
}
