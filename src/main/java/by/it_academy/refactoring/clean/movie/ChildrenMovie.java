package by.it_academy.refactoring.clean.movie;

import by.it_academy.refactoring.clean.charge.ChildrenChargeStrategy;
import by.it_academy.refactoring.clean.frequentrenterpoints.DefaultFrequentRenterPointsStrategy;

public class ChildrenMovie extends Movie {

    public ChildrenMovie(String title) {
        super(title, new ChildrenChargeStrategy(), new DefaultFrequentRenterPointsStrategy());
    }

}
