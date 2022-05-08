package by.it_academy.lesson2;

/**
 * @author Maxim Tereshchenko
 */
class Variables {

    private static boolean booleanField;
    private static char charField;
    private static byte byteField;
    private static short shortField;
    private static int intField;
    private static long longField;
    private static float floatField;
    private static double doubleField;
    private static String stringField;

    public static void main(String[] args) {
        System.out.println("Fields:");
        System.out.println("booleanField = " + booleanField);
        System.out.println("charField = " + charField);
        System.out.println("byteField = " + byteField);
        System.out.println("shortField = " + shortField);
        System.out.println("intField = " + intField);
        System.out.println("longField = " + longField);
        System.out.println("floatField = " + floatField);
        System.out.println("doubleField = " + doubleField);
        System.out.println("stringField = " + stringField);

        boolean booleanVar = true;
        char charVar = 'a';
        byte byteVar = 1;
        short shortVar = 2;
        int intVar = 3;
        long longVar = 4;
        float floatVar = 1.0f;
        double doubleVar = 2.0;
        String stringVar = "Maxim";

        System.out.println("Variables:");
        System.out.println("booleanVar = " + booleanVar);
        System.out.println("charVar = " + charVar);
        System.out.println("byteVar = " + byteVar);
        System.out.println("shortVar = " + shortVar);
        System.out.println("intVar = " + intVar);
        System.out.println("longVar = " + longVar);
        System.out.println("floatVar = " + floatVar);
        System.out.println("doubleVar = " + doubleVar);
        System.out.println("stringVar = " + stringVar);

        double first = 0.1;
        double second = 0.2;
        System.out.println("WARNING! 0.1 + 0.2 = " + (first + second));

        int age = 10;
        long longAge = age;
        short shortAge = (short) age;

        int someInt = 1_000_000;
        int binaryInt = 0b11101;
        int octagonalInt = 035;
        int hexagonalInt = 0x1D;

        System.out.println("Different integers:");
        System.out.println("someInt = " + someInt);
        System.out.println("binaryInt = " + binaryInt);
        System.out.println("octagonalInt = " + octagonalInt);
        System.out.println("hexagonalInt = " + hexagonalInt);

        char ch = 'a';
        int code = ch;
        char charFromCode = (char) code;

        System.out.println("Characters:");
        System.out.println("ch = " + ch);
        System.out.println("code = " + code);
        System.out.println("charFromCode = " + charFromCode);
    }
}
