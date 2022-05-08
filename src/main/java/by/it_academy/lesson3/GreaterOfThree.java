package by.it_academy.lesson3;

/**
 * @author Maxim Tereshchenko
 */
class GreaterOfThree {

    public static void main(String[] args) {
        int first = 1;
        int second = 2;
        int third = 3;

        if (first > second && first > third) {
            System.out.println("first");
        } else if (second > first && second > third) {
            System.out.println("second");
        } else {
            System.out.println("third");
        }
    }
}
