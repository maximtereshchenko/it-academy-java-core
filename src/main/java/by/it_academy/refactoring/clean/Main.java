package by.it_academy.refactoring.clean;

import by.it_academy.refactoring.clean.customer.Customer;
import by.it_academy.refactoring.clean.customer.Rental;
import by.it_academy.refactoring.clean.movie.ChildrenMovie;
import by.it_academy.refactoring.clean.movie.Movie;
import by.it_academy.refactoring.clean.movie.NewReleaseMovie;
import by.it_academy.refactoring.clean.movie.RegularMovie;
import by.it_academy.refactoring.clean.printformat.Json;

import java.util.List;

public class Main {

    public static final Movie REMBO = new RegularMovie("Rembo");
    public static final Movie LOTR = new NewReleaseMovie("Lord of the Rings");
    public static final Movie HARRY_POTTER = new ChildrenMovie("Harry Potter");

    public static void main(String[] args) {
        List<Rental> rentals = List.of(
                new Rental(REMBO, 1),
                new Rental(LOTR, 4),
                new Rental(HARRY_POTTER, 5)
        );

        Customer customer = new Customer("John Doe", rentals);

        System.out.println(new Json().print(customer));
    }

}
