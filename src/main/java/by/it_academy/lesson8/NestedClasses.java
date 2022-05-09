package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class NestedClasses {

    public static void main(String[] args) {
        var dataStructure = new DataStructure(new int[]{1, 2, 3, 4, 5});

        for (Integer number : dataStructure) {
            System.out.println(number);
        }
    }
}
