package by.it_academy.lesson6;

/**
 * @author Maxim Tereshchenko
 */
class Classes {

    public static void main(String[] args) {
        Worker human = new Human("Maxim");
        Worker robot = new Robot(10);

        System.out.println(human.name());
        System.out.println(robot.name());

        human.work();
        robot.work();

        //System.out.println(human.joke());
    }
}
