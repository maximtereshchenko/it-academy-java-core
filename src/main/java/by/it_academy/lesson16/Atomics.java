package by.it_academy.lesson16;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Maxim Tereshchenko
 */
final class Atomics {

    private static int number = 0;
    //private static AtomicInteger number = new AtomicInteger(0);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Set<Integer> set = new CopyOnWriteArraySet<Integer>();

        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                while (true) {
                    if (!set.add(number++)) {
                        System.out.println("Duplicate!");
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
    }
}
