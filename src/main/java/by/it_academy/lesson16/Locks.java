package by.it_academy.lesson16;

import java.util.concurrent.Executors;

/**
 * @author Maxim Tereshchenko
 */
final class Locks {

    public static void main(String[] args) {
        //deadlock();
        //livelock();
        starvation();
    }

    private static void deadlock() {
        var executorService = Executors.newFixedThreadPool(2);
        var resource1 = new Object();
        var resource2 = new Object();

        executorService.execute(() -> {
            synchronized (resource1) {
                System.out.println("First acquired resource1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource2) {
                    System.out.println("First acquired resource2");
                    System.out.println("Done!");
                }
            }
        });

        executorService.execute(() -> {
            synchronized (resource2) {
                System.out.println("Second acquired resource2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                synchronized (resource1) {
                    System.out.println("Second acquired resource1");
                    System.out.println("Done!");
                }
            }
        });
    }

    private static void livelock() {
        var executorService = Executors.newFixedThreadPool(2);
        var resource = new Object();
        var worker1 = new Worker(resource);
        var worker2 = new Worker(resource);
        worker1.setOther(worker2);
        worker2.setOther(worker1);

        executorService.execute(worker1);
        executorService.execute(worker2);
    }

    private static void starvation() {
        var resource = new Object();

        Runnable runnable = () -> {
            var counter = 0;
            while (counter++ != 100) {
                synchronized (resource) {
                    try {
                        //resource.wait(50);
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            System.out.println(Thread.currentThread().getName());
        };
        var executorService = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 3; i++) {
            executorService.execute(runnable);
        }
    }
}
