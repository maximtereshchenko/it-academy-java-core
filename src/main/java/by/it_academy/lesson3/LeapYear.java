package by.it_academy.lesson3;

class LeapYear {

    public static void main(String[] args) {
        int year = 2022;
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            System.out.println("yes");
        } else {
            System.out.println("no");
        }
    }
}
