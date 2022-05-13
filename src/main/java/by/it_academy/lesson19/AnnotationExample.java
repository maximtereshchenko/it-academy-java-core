package by.it_academy.lesson19;

import java.util.Arrays;

/**
 * @author Maxim Tereshchenko
 */
@MyAnnotation
@MyAnnotation
class AnnotationExample {

    public static void main(String[] args) throws NoSuchMethodException {
        var myAnnotations = AnnotationExample.class
                .getAnnotation(MyAnnotations.class)
                .value();

        var childMAnnotations = Child.class
                .getAnnotation(MyAnnotations.class)
                .value();

        System.out.println("myAnnotations = " + Arrays.toString(myAnnotations));
        System.out.println("childMAnnotations = " + Arrays.toString(childMAnnotations));
    }

    private static class Child extends AnnotationExample {

    }
}
