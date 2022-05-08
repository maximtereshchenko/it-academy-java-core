package by.it_academy.lesson3;

/**
 * Если число делится на 3 - напечатать Fizz,
 * если число делится на 5 - Buzz,
 * если и на 3, и на 5 - FizzBuzz,
 * иначе - само число.
 *
 * @author Maxim Tereshchenko
 */
class FizzBuzz {

    public static void main(String[] args) {
        int number = 1;

        if (number % 3 == 0) {
            if (number % 5 == 0) {
                System.out.println("FizzBuzz");
            } else {
                System.out.println("Fizz");
            }
        } else if (number % 5 == 0) {
            System.out.println("Buzz");
        } else {
            System.out.println(number);
        }
    }
}
