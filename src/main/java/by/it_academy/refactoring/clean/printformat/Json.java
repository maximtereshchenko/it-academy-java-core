package by.it_academy.refactoring.clean.printformat;

import by.it_academy.refactoring.clean.customer.Customer;
import by.it_academy.refactoring.clean.customer.Rental;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Collection;

public class Json implements PrintingFormat {

    @Override
    public String print(final Customer customer) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(new CustomerJson(customer));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class CustomerJson {

        private final String name;
        private final Collection<RentalJson> rentals = new ArrayList<>();

        CustomerJson(Customer customer) {
            name = customer.getName();
            for (Rental rental : customer.getRentals()) {
                rentals.add(new RentalJson(rental));
            }
        }

        public String getName() {
            return name;
        }

        public Collection<RentalJson> getRentals() {
            return rentals;
        }

    }

    private static final class RentalJson {

        private final String title;
        private final double charge;
        private final int frequentRenterPoints;

        RentalJson(Rental rental) {
            title = rental.getMovieTitle();
            charge = rental.getCharge();
            frequentRenterPoints = rental.getFrequentRenterPoints();
        }

        public String getTitle() {
            return title;
        }

        public double getCharge() {
            return charge;
        }

        public int getFrequentRenterPoints() {
            return frequentRenterPoints;
        }

    }

}
