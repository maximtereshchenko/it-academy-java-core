package by.it_academy.lesson19;

import java.lang.annotation.*;

/**
 * @author Maxim Tereshchenko
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@interface MyAnnotations {

    MyAnnotation[] value();
}

