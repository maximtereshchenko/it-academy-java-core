package by.it_academy.lesson16;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Maxim Tereshchenko
 */
final class Synchronization {

    public static void main(String[] args) {
        var executorService = Executors.newCachedThreadPool();
        var random = ThreadLocalRandom.current();
        var resource = new Resource();

        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                while (true) {
                    resource.put(random.nextInt(11));
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        executorService.execute(() -> {
            while (true) {
                resource.print();
                try {
                    Thread.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }
}
