package by.it_academy.lesson10.collections;

/**
 * @author Maxim Tereshchenko
 */
interface Collection<E> extends Iterable<E> {

    int size();

    boolean add(E element);

    boolean remove(E element);

    void clear();

    boolean contains(E element);

    E[] toArray();
}
