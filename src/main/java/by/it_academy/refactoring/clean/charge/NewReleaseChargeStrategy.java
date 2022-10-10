package by.it_academy.refactoring.clean.charge;

public class NewReleaseChargeStrategy implements ChargeStrategy {

    @Override
    public double getCharge(int daysRented) {
        return daysRented * 3.0;
    }

}
