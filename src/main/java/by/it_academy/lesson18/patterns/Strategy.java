package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface MovingStrategy {
    void move();
}

class Normal implements MovingStrategy {
    @Override
    public void move() {
        System.out.println("Moving at 100%");
    }
}

class Slowed implements MovingStrategy {
    @Override
    public void move() {
        System.out.println("Moving at 50%");
    }
}

class Rooted implements MovingStrategy {
    @Override
    public void move() {
        //empty
    }
}

class Player {
    private MovingStrategy movingStrategy = new Normal();

    void setMovingStrategy(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
    }

    void move() {
        movingStrategy.move();
    }
}

class Strategy {

    public static void main(String[] args) {
        Player player = new Player();
        player.move();
        player.setMovingStrategy(new Slowed());
        player.move();
        player.setMovingStrategy(new Rooted());
    }
}
