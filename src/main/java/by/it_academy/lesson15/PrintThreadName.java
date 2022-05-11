package by.it_academy.lesson15;

/**
 * @author Maxim Tereshchenko
 */
class PrintThreadName implements Runnable {

    @Override
    public void run() {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
