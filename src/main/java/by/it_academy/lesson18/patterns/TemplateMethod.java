package by.it_academy.lesson18.patterns;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Maxim Tereshchenko
 */
abstract class SortingAlgorithm {

    List<String> sorted() {
        return sort(initialCollection());
    }

    abstract Collection<String> initialCollection();

    abstract List<String> sort(Collection<String> collection);
}

class StaticCollectionSorting extends SortingAlgorithm {

    private final Collection<String> collection;

    StaticCollectionSorting(Collection<String> collection) {
        this.collection = collection;
    }

    @Override
    Collection<String> initialCollection() {
        return collection;
    }

    @Override
    List<String> sort(Collection<String> collection) {
        List<String> list = new ArrayList<>(collection);
        Collections.sort(list);
        return list;
    }
}

class RandomCollectionSorting extends SortingAlgorithm {

    @Override
    Collection<String> initialCollection() {
        return ThreadLocalRandom.current()
                .ints(5, 1, 4)
                .mapToObj(x -> "" + x)
                .collect(Collectors.toList());
    }

    @Override
    List<String> sort(Collection<String> collection) {
        return collection.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}

class TemplateMethod {

    public static void main(String[] args) {
        SortingAlgorithm staticCollectionSorting = new StaticCollectionSorting(List.of("3", "1", "2"));
        System.out.println("staticCollectionSorting.sorted() = " + staticCollectionSorting.sorted());

        SortingAlgorithm randomCollectionSorting = new RandomCollectionSorting();
        System.out.println("randomCollectionSorting.sorted() = " + randomCollectionSorting.sorted());
    }
}
