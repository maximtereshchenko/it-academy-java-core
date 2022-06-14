package by.it_academy.lesson15;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Maxim Tereshchenko
 */
class ExecutorServices {

    public static void main(String[] args) throws InterruptedException {
        //ExecutorService executorService = Executors.newCachedThreadPool();
        //ExecutorService executorService = Executors.newFixedThreadPool(3);
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                3,
                1,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName("name " + new Random().nextInt());
                    return thread;
                },
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 0; i < 10; i++) {
            executorService.execute(new PrintThreadName());
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
    }
}
