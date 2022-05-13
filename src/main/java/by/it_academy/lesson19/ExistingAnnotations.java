package by.it_academy.lesson19;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim Tereshchenko
 */
class ExistingAnnotations {

    public static void main(String[] args) {
        var deprecatedClass = new DeprecatedClass();
        safeVarargs(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    private static <T> T uncheckedCast(Object object) {
        return (T) object;
    }

    @SafeVarargs
    private static void safeVarargs(List<String>... lists) {
//        List[] unsafe = lists;
//        unsafe[0] = new ArrayList<Integer>();
    }

    @FunctionalInterface
    interface NotAFunctionalInterface {

        void method1();

        //void method2();
    }

    private static final class OverrideExample {

        //@Override
        void method() {

        }
    }
}

//@Deprecated
class DeprecatedClass {}
