package by.it_academy.lesson9;

/**
 * @author Maxim Tereshchenko
 */
enum DayOfWeek {
    MONDAY(true),
    TUESDAY(true),
    WEDNESDAY(true),
    THURSDAY(true),
    FRIDAY(true),
    SATURDAY(false),
    SUNDAY(false);

    static {
        System.out.println("static");
    }

    private final boolean workingDay;

    {
        System.out.println("instance");
    }

    DayOfWeek(boolean workingDay) {
        this.workingDay = workingDay;
    }

    boolean isWorkingDay() {
        return workingDay;
    }
}
