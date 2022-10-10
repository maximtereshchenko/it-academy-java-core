package by.it_academy.refactoring.clean.movie;

import by.it_academy.refactoring.clean.charge.RegularChargeStrategy;
import by.it_academy.refactoring.clean.frequentrenterpoints.DefaultFrequentRenterPointsStrategy;

public class RegularMovie extends Movie {

    public RegularMovie(String title) {
        super(title, new RegularChargeStrategy(), new DefaultFrequentRenterPointsStrategy());
    }

}
