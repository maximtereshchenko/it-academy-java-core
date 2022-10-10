package by.it_academy.refactoring.clean.movie;

import by.it_academy.refactoring.clean.charge.NewReleaseChargeStrategy;
import by.it_academy.refactoring.clean.frequentrenterpoints.NewReleaseFrequentRenterPointsStrategy;

public class NewReleaseMovie extends Movie {

    public NewReleaseMovie(String title) {
        super(title, new NewReleaseChargeStrategy(), new NewReleaseFrequentRenterPointsStrategy());
    }

}
