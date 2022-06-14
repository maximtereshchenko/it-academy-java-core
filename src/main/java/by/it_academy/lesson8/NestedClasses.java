package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class NestedClasses {

    public static void main(String[] args) {
        DataStructure dataStructure = new DataStructure(new int[]{1, 2, 3, 4, 5});
        Iterator iterator = dataStructure.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
