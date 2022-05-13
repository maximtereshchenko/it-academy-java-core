package by.it_academy.lesson19;

/**
 * @author Maxim Tereshchenko
 */
interface TextFile {

    String text();

    String number();

    @DefaultValue("default")
    String notThere();
}
