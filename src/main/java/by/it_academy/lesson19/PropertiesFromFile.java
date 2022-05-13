package by.it_academy.lesson19;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @author Maxim Tereshchenko
 */
class PropertiesFromFile implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        var fileName = proxy.getClass().getInterfaces()[0].getSimpleName().toLowerCase();
        try (var stream = new FileInputStream(fileName)) {
            var properties = new Properties();
            properties.load(stream);
            if (properties.containsKey(method.getName())) {
                return properties.getProperty(method.getName());
            }

            if (method.isAnnotationPresent(DefaultValue.class)) {
                return method.getAnnotation(DefaultValue.class).value();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
