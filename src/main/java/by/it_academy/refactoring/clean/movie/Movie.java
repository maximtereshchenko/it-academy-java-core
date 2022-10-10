package by.it_academy.refactoring.clean.movie;

import by.it_academy.refactoring.clean.charge.ChargeStrategy;
import by.it_academy.refactoring.clean.frequentrenterpoints.FrequentRenterPointsStrategy;

public abstract class Movie {

    private final String title;
    private final ChargeStrategy chargeStrategy;
    private final FrequentRenterPointsStrategy frequentRenterPointsStrategy;

    Movie(String title, ChargeStrategy chargeStrategy, FrequentRenterPointsStrategy frequentRenterPointsStrategy) {
        this.title = title;
        this.chargeStrategy = chargeStrategy;
        this.frequentRenterPointsStrategy = frequentRenterPointsStrategy;
    }

    public String getTitle() {
        return title;
    }

    public int getFrequentRenterPoints(int daysRented) {
        return frequentRenterPointsStrategy.getFrequentRenterPoints(daysRented);
    }

    public double getCharge(int daysRented) {
        return chargeStrategy.getCharge(daysRented);
    }

}