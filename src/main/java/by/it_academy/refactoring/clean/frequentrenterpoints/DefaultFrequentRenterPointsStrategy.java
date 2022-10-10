package by.it_academy.refactoring.clean.frequentrenterpoints;

public class DefaultFrequentRenterPointsStrategy implements FrequentRenterPointsStrategy {

    @Override
    public int getFrequentRenterPoints(final int daysRented) {
        return 1;
    }

}
