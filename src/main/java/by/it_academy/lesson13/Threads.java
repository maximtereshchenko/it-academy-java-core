package by.it_academy.lesson13;

/**
 * @author Maxim Tereshchenko
 */
class Threads {

    public static void main(String[] args) throws InterruptedException {
        states();
        interruption();
        runnable();
    }

    private static void runnable() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Complete");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
    }

    private static void interruption() throws InterruptedException {
        Thread endlessThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Running endlessly");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println("interrupted");
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        };
        endlessThread.start();
        Thread.sleep(500);
        endlessThread.interrupt();
    }

    private static void states() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Complete");
            }
        };
        thread.setDaemon(true);
        System.out.println("thread.getState() = " + thread.getState());
        thread.start();
        System.out.println("thread.getState() = " + thread.getState());
        thread.join();
        System.out.println("thread.getState() = " + thread.getState());
    }
}
