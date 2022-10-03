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

    private final boolean workingDay;

    DayOfWeek(boolean workingDay) {
        this.workingDay = workingDay;
    }

    boolean isWorkingDay() {
        return workingDay;
    }
}
