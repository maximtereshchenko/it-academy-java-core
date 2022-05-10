package by.it_academy.lesson10;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author Maxim Tereshchenko
 */
class ArrayList<E> implements List<E> {

    private E[] array = emptyArray(1);
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(E element) {
        extendIfNecessary();
        array[size++] = element;
        return true;
    }

    @Override
    public boolean remove(E element) {
        var index = indexOf(element);

        if (index == -1) {
            return false;
        }

        return remove(index) != null;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E[] toArray() {
        var result = emptyArray(size);
        System.arraycopy(array, 0, result, 0, size);
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return array[index++];
            }
        };
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        Objects.checkIndex(index, size);
        var previous = array[index];
        array[index] = element;
        return previous;
    }

    @Override
    public void add(int index, E element) {
        Objects.checkIndex(index, size);
        E replaced = element;
        for (int i = index; i < size; i++) {
            replaced = set(i, replaced);
        }
        add(replaced);
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, size);
        var element = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return element;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], element)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(array, size));
    }

    private void extendIfNecessary() {
        if (size < array.length) {
            return;
        }

        var newArray = emptyArray(array.length + 1);
        System.arraycopy(array, 0, newArray, 0, array.length);

        array = newArray;
    }

    private E[] emptyArray(int size) {
        return (E[]) new Object[size];
    }
}
