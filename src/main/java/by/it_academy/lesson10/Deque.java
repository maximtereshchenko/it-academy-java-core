package by.it_academy.lesson10;

/**
 * @author Maxim Tereshchenko
 */
interface Deque<E> extends Queue<E> {

    boolean offerLast(E element);

    E pollFirst();

    E peekFirst();
}
