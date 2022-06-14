package by.it_academy.lesson7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maxim Tereshchenko
 */
class Regex {

    public static void main(String[] args) {
        String text = "ajf abc 123 hfhf -45 afhdfh fdfsf 5 aqw f sf 5 5 -6";

        System.out.println(numbers(text));
        System.out.println(startingWithA(text));
        System.out.println(isEmail("test@gmail.com"));
        System.out.println(isEmail("my.name@mail.ru"));
        System.out.println(isEmail("my-name@inbox.ru"));
        System.out.println(replaceDashes("some-words-with-dashes+and+pluses"));
    }

    private static String replaceDashes(String text) {
        return text.replaceAll("[-+]", " ");
    }

    private static String numbers(String text) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(text);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            builder.append(matcher.group())
                    .append(',');
        }

        return builder.deleteCharAt(builder.length() - 1)
                .toString();
    }

    private static String startingWithA(String text) {
        Pattern pattern = Pattern.compile("a\\w+");
        Matcher matcher = pattern.matcher(text);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            builder.append(matcher.group())
                    .append(',');
        }

        return builder.deleteCharAt(builder.length() - 1)
                .toString();
    }

    private static boolean isEmail(String text) {
        return text.matches("[a-zA-Z._]+@(mail|gmail|yandex)\\.(ru|com)");
    }
}
