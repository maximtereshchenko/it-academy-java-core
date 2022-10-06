package by.it_academy.lesson10;

import java.util.Iterator;

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
        int index = indexOf(element);
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
        E[] result = emptyArray(size);
        for (int i = 0; i < size; i++) {
            result[i] = array[i];
        }
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
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        E previous = array[index];
        array[index] = element;
        return previous;
    }

    @Override
    public void add(int index, E element) {
        E replaced = element;
        for (int i = index; i < size; i++) {
            replaced = set(i, replaced);
        }
        add(replaced);
    }

    @Override
    public E remove(int index) {
        E element = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[--size] = null;
        return element;
    }

    @Override
    public int indexOf(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(array[i]).append(", ");
        }
        return builder.delete(builder.length() - 2, builder.length())
                .append(']')
                .toString();
    }

    private void extendIfNecessary() {
        if (size < array.length) {
            return;
        }
        E[] extended = emptyArray(array.length * 2);
        for (int i = 0; i < array.length; i++) {
            extended[i] = array[i];
        }
        array = extended;
    }

    private E[] emptyArray(int size) {
        return (E[]) new Object[size];
    }
}
