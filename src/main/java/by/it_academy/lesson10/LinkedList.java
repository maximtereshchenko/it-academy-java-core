package by.it_academy.lesson10;

import java.util.Iterator;

/**
 * @author Maxim Tereshchenko
 */
class LinkedList<E> implements List<E> {

    private Node<E> head;

    @Override
    public int size() {
        if (head == null) {
            return 0;
        }

        Node<E> current = head;
        int size = 1;
        while (current.next != null) {
            size++;
            current = current.next;
        }

        return size;
    }

    @Override
    public boolean add(E element) {
        Node<E> node = new Node<E>();
        node.value = element;
        if (head == null) {
            head = node;
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = node;
        }
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
        head = null;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
    }

    @Override
    public E[] toArray() {
        Object[] result = new Object[size()];
        int index = 0;
        Iterator<E> iterator = iterator();
        while (iterator.hasNext()) {
            result[index++] = iterator.next();
        }
        return (E[]) result;
    }

    @Override
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E replaced = current.value;
        current.value = element;
        return replaced;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = new Node<E>();
        node.value = element;
        if (index == 0) {
            node.next = head;
            head = node;
            return;
        }
        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        Node<E> next = current.next;
        current.next = node;
        node.next = next;
    }

    @Override
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            Node<E> old = head;
            head = head.next;
            return old.value;
        }
        Node<E> current = head;
        for (int i = 0; i < index - 1; i++) {
            current = current.next;
        }
        Node<E> old = current.next;
        current.next = old.next;
        return old.value;
    }

    @Override
    public int indexOf(E element) {
        Node<E> current = head;
        int index = 0;
        while (current.value != element) {
            index++;
            current = current.next;
            if (current == null) {
                return -1;
            }
        }
        return index;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (E e : this) {
            builder.append(e)
                    .append(',');
        }
        if (builder.length() == 1) {
            return "[]";
        }
        return builder.deleteCharAt(builder.length() - 1)
                .append(']')
                .toString();
    }

    private static final class Node<E> {

        private Node<E> next;
        private E value;
    }
}
