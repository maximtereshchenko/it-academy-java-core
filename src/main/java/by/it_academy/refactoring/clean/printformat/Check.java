package by.it_academy.refactoring.clean.printformat;

import by.it_academy.refactoring.clean.customer.Customer;
import by.it_academy.refactoring.clean.customer.Rental;

import java.util.List;

public class Check implements PrintingFormat {

    @Override
    public String print(final Customer customer) {
        StringBuilder builder = new StringBuilder("Rental Record for ")
                .append(customer.getName())
                .append(System.lineSeparator());
        for (Rental rental : customer.getRentals()) {
            builder.append('\t')
                    .append(rental.getMovieTitle())
                    .append('\t')
                    .append(rental.getCharge())
                    .append(System.lineSeparator());
        }
        return builder.append("Amount owed is ")
                .append(getTotalAmount(customer.getRentals()))
                .append(System.lineSeparator())
                .append("You earned ")
                .append(getTotalFrequentRenterPoints(customer.getRentals()))
                .append(" frequent renter points")
                .toString();
    }

    private double getTotalAmount(List<Rental> rentals) {
        double sum = 0;
        for (Rental rental : rentals) {
            sum += rental.getCharge();
        }
        return sum;
    }

    private int getTotalFrequentRenterPoints(List<Rental> rentals) {
        int sum = 0;
        for (Rental rental : rentals) {
            sum += rental.getFrequentRenterPoints();
        }
        return sum;
    }

}
