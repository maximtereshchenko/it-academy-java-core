package by.it_academy.lesson6;

/**
 * @author Maxim Tereshchenko
 */
class Robot extends AbstractWorker {

    private final int speed;

    Robot(int speed) {
        super("Robot");
        this.speed = speed;
    }

    @Override
    public void work() {
        System.out.println("Doing the work automatically at " + speed + " speed");
    }
}
