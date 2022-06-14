package by.it_academy.lesson15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Tereshchenko
 */
class CompletableFutures {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();

        try {
            CompletableFuture<String> message = CompletableFuture.supplyAsync(CompletableFutures::message, service)
                    .exceptionally(e -> {
                        System.out.println("Could not find create message");
                        return "default message";
                    });
            CompletableFuture.supplyAsync(CompletableFutures::findReceiver, service)
                    .exceptionally(e -> {
                        System.out.println("Could not find receiver");
                        return "default receiver";
                    })
                    .thenAcceptBothAsync(message, CompletableFutures::send, service)
                    .whenCompleteAsync(
                            (unused, e) -> {
                                if (e != null) {
                                    System.out.println("Could not send");
                                    return;
                                }
                                System.out.println(notification());
                            },
                            service
                    )
                    .get();
        } finally {
            service.shutdown();
            service.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    private static String findReceiver() {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("Finding receiver");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (true) throw new RuntimeException();
        return "Maxim";
    }

    private static String message() {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("Creating message");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (true) throw new RuntimeException();
        return "Message";
    }

    private static void send(String receiver, String message) {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        System.out.println("Sending " + message + " to " + receiver);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (true) throw new RuntimeException();
    }

    private static String notification() {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        return "OK";
    }
}
