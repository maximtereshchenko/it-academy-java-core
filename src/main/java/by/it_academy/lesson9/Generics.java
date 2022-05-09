package by.it_academy.lesson9;

/**
 * @author Maxim Tereshchenko
 */
class Generics {

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<>();
        Box<? extends Number> numberBox = integerBox;

        Number number = numberBox.getItem();
        //numberBox.setItem(1);
        Integer integer = integerBox.getItem();
        integerBox.setItem(2);

        Integer[] integers = new Integer[]{1, 2, 3};
        Object[] objects = integers;

        objects[0] = new Object();

        Object object0 = objects[0];
        Object object1 = objects[1];

        Integer integer0 = integers[0];
        Integer integer1 = integers[1];
    }

    public void doWork(Box<? extends Number> box) {
        Number item = box.getItem();
        //box.setItem(1);
    }

    public void setItem(Box<? super Integer> box, Integer item) {
        box.setItem(item);
        Object object = box.getItem();
    }
}
