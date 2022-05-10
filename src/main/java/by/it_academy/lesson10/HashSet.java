package by.it_academy.lesson10;

import java.util.Iterator;

/**
 * @author Maxim Tereshchenko
 */
class HashSet<E> implements Set<E> {

    private final Map<E, Object> map = new HashMap<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean add(E element) {
        if (map.containsKey(element)) {
            return false;
        }
        map.put(element, null);
        return true;
    }

    @Override
    public boolean remove(E element) {
        if (map.containsKey(element)) {
            map.remove(element);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public E[] toArray() {
        var result = new Object[size()];
        var index = 0;
        var iterator = iterator();
        while (iterator.hasNext()) {
            result[index++] = iterator.next();
        }
        return (E[]) result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            private final Iterator<Map.Entry<E, Object>> original = map.iterator();

            @Override
            public boolean hasNext() {
                return original.hasNext();
            }

            @Override
            public E next() {
                return original.next().getKey();
            }
        };
    }

    @Override
    public String toString() {
        var builder = new StringBuilder("[");
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
}
