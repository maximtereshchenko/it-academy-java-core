package by.it_academy.lesson10;

/**
 * @author Maxim Tereshchenko
 */
interface List<E> extends Collection<E> {

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(E element);
}
