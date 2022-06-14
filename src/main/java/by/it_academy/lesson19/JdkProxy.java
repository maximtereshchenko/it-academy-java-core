package by.it_academy.lesson19;

import java.lang.reflect.Proxy;

/**
 * @author Maxim Tereshchenko
 */
class JdkProxy {

    public static void main(String[] args) {
        TextFile instance = (TextFile) Proxy.newProxyInstance(JdkProxy.class.getClassLoader(), new Class[]{TextFile.class}, new PropertiesFromFile());

        System.out.println("instance.number() = " + instance.number());
        System.out.println("instance.text() = " + instance.text());
        System.out.println("instance.notThere() = " + instance.notThere());
    }
}
