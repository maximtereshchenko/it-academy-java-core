package by.it_academy.lesson16;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Maxim Tereshchenko
 */
class Resource {

    private final Queue<Integer> queue = new ArrayDeque<>();
    //private final Queue<Integer> queue = new ConcurrentLinkedQueue<>();

    void put(int number) {
        queue.add(number);
    }

    void print() {
        queue.forEach(System.out::print);
        System.out.println();
    }
}
