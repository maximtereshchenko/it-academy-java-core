package by.it_academy.lesson10.collections;

/**
 * @author Maxim Tereshchenko
 */
interface Queue<E> extends Collection<E> {

    boolean offerFirst(E element);

    E pollLast();

    E peekLast();
}
