package by.it_academy.refactoring.clean.charge;

public class ChildrenChargeStrategy implements ChargeStrategy {

    @Override
    public double getCharge(final int daysRented) {
        return 1.5 + getAdditionalFee(daysRented);
    }

    private double getAdditionalFee(int daysRented) {
        if (daysRented < 4) {
            return 0;
        }
        return (daysRented - 3) * 1.5;
    }

}
