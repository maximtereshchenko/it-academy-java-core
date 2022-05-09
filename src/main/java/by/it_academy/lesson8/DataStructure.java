package by.it_academy.lesson8;

import java.util.Iterator;

/**
 * @author Maxim Tereshchenko
 */
class DataStructure implements Iterable<Integer> {

    private final int[] array;

    DataStructure(int[] array) {
        this.array = array;
    }

    @Override
    public Iterator<Integer> iterator() {
        class LocalIterator implements Iterator<Integer> {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                return array[index++];
            }
        }

        var anonymousIterator = new Iterator<Integer>() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public Integer next() {
                return array[index++];
            }
        };

        return anonymousIterator;
    }

    private static class StaticNestedIterator implements Iterator<Integer> {

        private final int[] array;
        private int index = 0;

        private StaticNestedIterator(int[] array) {
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public Integer next() {
            return array[index++];
        }
    }

    private class InnerIterator implements Iterator<Integer> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public Integer next() {
            return array[index++];
        }
    }
}
