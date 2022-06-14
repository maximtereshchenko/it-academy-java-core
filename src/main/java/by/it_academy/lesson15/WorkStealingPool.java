package by.it_academy.lesson15;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Maxim Tereshchenko
 */
class WorkStealingPool {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random random = ThreadLocalRandom.current();

        ExecutorService executorService = Executors.newWorkStealingPool();

        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();

            int sum = executorService.submit(() ->
                            random.ints()
                                    .parallel()
                                    .limit(1000000000)
                                    .sum()
                    )
                    .get();

            System.out.println("" + sum + " " + (System.currentTimeMillis() - start));
        }
    }
}
