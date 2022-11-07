package by.it_academy.lesson19;

import by.it_academy.Builder;

final class Person {

    private final String firstName;
    private final String lastName;
    private final int age;

    @Builder
    Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
