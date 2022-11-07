package by.it_academy.lesson19;

final class AnnotationProcessorExample {

    public static void main(String[] args) {
        Person person = new PersonBuilder()
                .withAge(1)
                .withFirstName("first")
                .withLastName("frst")
                .build();
        System.out.println(person);
    }
}
