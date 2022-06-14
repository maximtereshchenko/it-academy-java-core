package by.it_academy.lesson9;

import java.util.Arrays;

/**
 * @author Maxim Tereshchenko
 */
class Enums {

    public static void main(String[] args) {
        DayOfWeek monday = DayOfWeek.MONDAY;

        System.out.println("monday.name() = " + monday.name());
        System.out.println("monday.ordinal() = " + monday.ordinal());
        System.out.println("DayOfWeek.valueOf(\"TUESDAY\") = " + DayOfWeek.valueOf("TUESDAY"));
        System.out.println("DayOfWeek.values() = " + Arrays.toString(DayOfWeek.values()));
        System.out.println("monday == DayOfWeek.MONDAY = " + (monday == DayOfWeek.MONDAY));
        System.out.println("monday.isWorkingDay() = " + monday.isWorkingDay());
    }
}
