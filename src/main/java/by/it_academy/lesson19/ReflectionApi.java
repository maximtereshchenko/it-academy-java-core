package by.it_academy.lesson19;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Maxim Tereshchenko
 */
class ReflectionApi {

    private int number = 10;

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ReflectionApi target = new ReflectionApi();
        for (Method method : target.getClass().getDeclaredMethods()) {
            System.out.println(method);
        }
        for (Field field : target.getClass().getDeclaredFields()) {
            System.out.println(field);
        }
        Field field = target.getClass().getDeclaredField("number");
        System.out.println("target.getNumber() = " + target.getNumber());
        field.set(target, 20);
        System.out.println("target.getNumber() = " + target.getNumber());
        Method method = target.getClass().getDeclaredMethod("setNumber", int.class);
        method.invoke(target, 30);
        System.out.println("target.getNumber() = " + target.getNumber());
    }

    int getNumber() {
        return number;
    }

    void setNumber(int number) {
        this.number = number;
    }
}
