package by.it_academy.refactoring.clean.charge;

public class RegularChargeStrategy implements ChargeStrategy {

    @Override
    public double getCharge(int daysRented) {
        return 2 + getAdditionalFee(daysRented);
    }

    private double getAdditionalFee(int daysRented) {
        if (daysRented < 3) {
            return 0;
        }
        return (daysRented - 2) * 1.5;
    }

}
