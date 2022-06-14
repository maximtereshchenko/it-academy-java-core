package by.it_academy.lesson8;

/**
 * @author Maxim Tereshchenko
 */
class DataStructure {

    private final int[] array;

    DataStructure(int[] array) {
        this.array = array;
    }

    public Iterator iterator() {
        class LocalIterator implements Iterator {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public int next() {
                return array[index++];
            }
        }

        var anonymousIterator = new Iterator() {

            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            @Override
            public int next() {
                return array[index++];
            }
        };

        return anonymousIterator;
    }

    private static class StaticNestedIterator implements Iterator {

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
        public int next() {
            return array[index++];
        }
    }

    private class InnerIterator implements Iterator {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < array.length;
        }

        @Override
        public int next() {
            return array[index++];
        }
    }
}
