package by.it_academy.lesson19;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Maxim Tereshchenko
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface DefaultValue {

    String value();
}
