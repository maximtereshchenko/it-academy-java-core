package by.it_academy.lesson9;

/**
 * @author Maxim Tereshchenko
 */
class Box<T> {

    private T item;

    T getItem() {
        return item;
    }

    void setItem(T item) {
        this.item = item;
    }
}
