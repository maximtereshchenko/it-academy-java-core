package by.it_academy.refactoring.clean.customer;

import by.it_academy.refactoring.clean.movie.Movie;

public class Rental {

    private final Movie movie;
    private final int daysRented;

    public Rental(Movie movie, int daysRented) {
        this.movie = movie;
        this.daysRented = daysRented;
    }

    public String getMovieTitle() {
        return movie.getTitle();
    }

    public int getFrequentRenterPoints() {
        return movie.getFrequentRenterPoints(daysRented);
    }

    public double getCharge() {
        return movie.getCharge(daysRented);
    }

}
