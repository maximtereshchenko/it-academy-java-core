package by.it_academy.lesson15;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Tereshchenko
 */
class ScheduledTasks {

    public static void main(String[] args) {
        var executorService = Executors.newScheduledThreadPool(3);

        executorService.schedule(
                () -> System.out.println("scheduled " + Instant.now()),
                5,
                TimeUnit.SECONDS
        );

        executorService.scheduleAtFixedRate(
                () -> {
                    System.out.println("fixed rate " + Instant.now());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("fixed rate is over");
                },
                0,
                10,
                TimeUnit.SECONDS
        );

        executorService.scheduleWithFixedDelay(
                () -> {
                    System.out.println("fixed delay " + Instant.now());
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("fixed delay is over");
                },
                0,
                10,
                TimeUnit.SECONDS
        );
    }
}
