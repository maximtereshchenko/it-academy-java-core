package by.it_academy.lesson2;

/**
 * @author Maxim Tereshchenko
 */
class Operators {

    public static void main(String[] args) {
        int a = 5;
        int b = 3;

        System.out.println("Arithmetic operators");
        System.out.println("5 + 3 = " + (a + b));
        System.out.println("5 - 3 = " + (a - b));
        System.out.println("5 * 3 = " + (a * b));
        System.out.println("5 / 3 = " + (a / b));
        System.out.println("5 % 3 = " + (a % b));
        System.out.println("a++ = " + (a++));
        System.out.println("++b = " + (++b));
        System.out.println("a-- = " + (a--));
        System.out.println("--b = " + (--b));
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        int binaryA = 0b1111100;
        int binaryB = 0b0001111;
        System.out.println("Bitwise operators");
        System.out.println("binaryA = " + binary(binaryA));
        System.out.println("binaryB = " + binary(binaryB));
        System.out.println("A & B = " + binary(binaryA & binaryB));
        System.out.println("A | B = " + binary(binaryA | binaryB));
        System.out.println("A ^ B = " + binary(binaryA ^ binaryB));
        System.out.println("~A = " + binary(~binaryA));
        System.out.println("A << 2 = " + binary(binaryA << 2));
        System.out.println("MAX_VALUE = " + binary(Integer.MAX_VALUE));
        System.out.println("MIN_VALUE = " + binary(Integer.MIN_VALUE));
        System.out.println("MIN_VALUE >> 3 = " + binary(Integer.MIN_VALUE >> 3));
        System.out.println("MIN_VALUE >>> 3 = " + binary(Integer.MIN_VALUE >>> 3));
        System.out.println("MAX_VALUE >> 3 = " + binary(Integer.MAX_VALUE >> 3));
        System.out.println("MAX_VALUE >>> 3 = " + binary(Integer.MAX_VALUE >>> 3));

        int x = 5;
        System.out.println("Assignment operators:");
        System.out.println("x = " + x);
        x += 3;
        System.out.println("x = " + x);

        int first = 5;
        int second = 3;
        System.out.println("Comparison operators:");
        System.out.println("5 == 3 - " + (first == second));
        System.out.println("5 != 3 - " + (first != second));
        System.out.println("5 > 3 - " + (first > second));
        System.out.println("5 < 3 - " + (first < second));
        System.out.println("first >= 5 - " + (first >= 5));
        System.out.println("second <= 3 - " + (second <= 3));
        System.out.println("new Object() == new Object() - " + (new Object() == new Object()));

        System.out.println("Logical operators:");
        System.out.println("5 < 6 && 5 < 10 - " + (first < 6 && first < 10));
        System.out.println("5 < 6 || 5 > 10 - " + (first < 6 || first > 10));
        System.out.println("!(5 < 6 && 5 < 10) - " + !(first < 6 && first < 10));
    }

    private static String binary(int number) {
        return String.format("%1$32s", Integer.toBinaryString(number)).replace(' ', '0');
    }
}
