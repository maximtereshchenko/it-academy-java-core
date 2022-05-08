package by.it_academy.lesson3;

/**
 * @author Maxim Tereshchenko
 */
class WorkingDay {

    public static void main(String[] args) {
        int dayOfWeek = 1;

        switch (dayOfWeek) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                System.out.println("Yes");
                break;
            case 6:
            case 7:
                System.out.println("No");
                break;
            default:
                System.out.println("Incorrect number");
        }
    }
}
