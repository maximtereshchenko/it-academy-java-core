package by.it_academy.lesson9;

/**
 * @author Maxim Tereshchenko
 */
class Boxing {

    public static void main(String[] args) {
        Integer wrapper = 1;
        int primitive = Integer.valueOf(1);
        int parsed = Integer.parseInt("1");
        long asLong = wrapper.longValue();

        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;

        for (int i = 0; i < 5; i++) {
            var start = System.currentTimeMillis();
            boxed();
            System.out.println("Boxed " + (System.currentTimeMillis() - start));
        }

        for (int i = 0; i < 5; i++) {
            var start = System.currentTimeMillis();
            primitive();
            System.out.println("Primitive " + (System.currentTimeMillis() - start));
        }
    }

    private static int boxed() {
        Integer result = 0;

        for (int i = 0; i < 1000000; i++) {
            result += i;
        }

        return result;
    }

    private static int primitive() {
        int result = 0;

        for (int i = 0; i < 1000000; i++) {
            result += i;
        }

        return result;
    }
}
