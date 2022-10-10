package by.it_academy.refactoring.clean.frequentrenterpoints;

public class NewReleaseFrequentRenterPointsStrategy implements FrequentRenterPointsStrategy {

    @Override
    public int getFrequentRenterPoints(int daysRented) {
        if (daysRented > 1) {
            return 2;
        }
        return 1;
    }

}
