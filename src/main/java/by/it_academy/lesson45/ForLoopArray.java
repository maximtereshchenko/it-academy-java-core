package by.it_academy.lesson45;

/**
 * @author Maxim Tereshchenko
 */
class ForLoopArray {

    public static void main(String[] args) {
        int[] array = new int[5];

        System.out.println("Before");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        System.out.println("After");
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
