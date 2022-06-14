package by.it_academy.lesson10;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Maxim Tereshchenko
 */
class HashMap<K, V> implements Map<K, V> {

    private Node<K, V>[] array = new Node[1];

    @Override
    public int size() {
        int size = 0;
        for (Entry<K, V> ignored : this) {
            size++;
        }
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        for (Entry<K, V> entry : this) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        for (Entry<K, V> entry : this) {
            if (entry.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = array[bucket(key)];
        while (node != null && !node.getKey().equals(key)) {
            node = node.next;
        }
        return node == null ? null : node.getValue();
    }

    @Override
    public V put(K key, V value) {
        extendIfNecessary();
        int bucket = bucket(key);
        Node<K, V> current = array[bucket];
        if (current == null) {
            Node<K, V> node = new Node<K, V>(key);
            node.value = value;
            array[bucket] = node;
            return null;
        }
        while (!current.getKey().equals(key)) {
            if (current.next == null) {
                Node<K, V> node = new Node<K, V>(key);
                node.value = value;
                current.next = node;
                return null;
            }
        }
        V replaced = current.value;
        current.value = value;
        return replaced;
    }

    @Override
    public V remove(K key) {
        int bucket = bucket(key);
        Node<K, V> node = array[bucket];
        if (node.getKey().equals(key)) {
            array[bucket] = node.next;
            return node.getValue();
        }
        Node<K, V> previous = node;
        Node<K, V> current = node.next;
        while (current != null && !current.getKey().equals(key)) {
            previous = current;
            current = current.next;
        }
        if (current == null) {
            return null;
        }
        previous.next = current.next;
        return current.getValue();
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                int finalIndex = i;
                return new Iterator<>() {

                    private int index = finalIndex;
                    private Node<K, V> node = array[index];

                    @Override
                    public boolean hasNext() {
                        return node != null;
                    }

                    @Override
                    public Node<K, V> next() {
                        Node<K, V> result = node;
                        if (node.next != null) {
                            node = node.next;
                        } else {
                            while (++index < array.length && array[index] == null) ;
                            node = index == array.length ? null : array[index];
                        }
                        return result;
                    }
                };
            }
        }
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Entry<K, V> next() {
                return null;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (Entry<K, V> node : this) {
            builder.append(node)
                    .append(',');
        }
        if (builder.length() == 1) {
            return "[]";
        }
        return builder.deleteCharAt(builder.length() - 1)
                .append(']')
                .toString();
    }

    private void extendIfNecessary() {
        int haveElements = 0;
        for (Node<K, V> node : array) {
            if (node != null) {
                haveElements++;
            }
        }

        if ((double) haveElements / array.length < 0.75) {
            return;
        }

        Node<K, V>[] old = array;
        array = new Node[old.length * 2];
        for (Node<K, V> node : old) {
            if (node != null) {
                Node<K, V> current = node;
                while (current != null) {
                    put(current.getKey(), current.getValue());
                    current = current.next;
                }
            }
        }
    }

    private int bucket(K key) {
        return key.hashCode() % array.length;
    }

    private static final class Node<K, V> implements Entry<K, V> {

        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return key.toString() + '=' + value;
        }
    }
}
