package by.it_academy.lesson16;

/**
 * @author Maxim Tereshchenko
 */
final class Worker implements Runnable {

    private final Object resource;
    private boolean active = true;
    private Worker other;

    Worker(Object resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (resource) {
                System.out.println("Working " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                if (!other.active) {
                    System.out.println("Done " + Thread.currentThread().getName());
                    active = false;
                    return;
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void setOther(Worker other) {
        this.other = other;
    }
}
