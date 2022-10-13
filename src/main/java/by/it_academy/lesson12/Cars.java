package by.it_academy.lesson12;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Cars {

    public static void main(String[] args) {
        Collection<Car> cars = getCars();
        for (Car car : cars) {
            if (isInteresting(car)) {
                System.out.println(car);
            }
        }
    }

    private static Collection<Car> getCars() {
        try (Scanner scanner = new Scanner(Paths.get("src/main/resources/cars.csv"))) {
            for (int i = 0; i < 2; i++) {
                scanner.nextLine();
            }
            Collection<Car> cars = new ArrayList<>();
            while (scanner.hasNextLine()) {
                cars.add(new Car(scanner.nextLine()));
            }
            return cars;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isInteresting(Car car) {
        return car.getName().contains("Ford") && car.getHorsepower() > 80 && car.getHorsepower() < 90;
    }

    private static class Car {

        private final String line;

        public Car(String line) {
            this.line = line;
        }

        public String getName() {
            return getParts()[0];
        }

        public double getHorsepower() {
            return Double.parseDouble(getParts()[4]);
        }

        private String[] getParts() {
            return line.split(";");
        }

        @Override
        public String toString() {
            return getName() + " " + getHorsepower();
        }
    }
}
