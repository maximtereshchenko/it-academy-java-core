package by.it_academy.lesson4_5;

/**
 * @author Maxim Tereshchenko
 */
class WhileLoopArray {

    public static void main(String[] args) {
        var array = new int[]{1, 2, 3, 4, 5};
        int index = 0;

        while (index < array.length) {
            System.out.println(array[index]);
            index++;
        }
    }
}
