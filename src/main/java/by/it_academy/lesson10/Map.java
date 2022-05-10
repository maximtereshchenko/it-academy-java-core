package by.it_academy.lesson10;

/**
 * @author Maxim Tereshchenko
 */
interface Map<K, V> extends Iterable<Map.Entry<K, V>> {

    int size();

    boolean containsKey(K key);

    boolean containsValue(V value);

    V get(K key);

    V put(K key, V value);

    V remove(K key);

    void clear();

    interface Entry<K, V> {

        K getKey();

        V getValue();
    }
}
