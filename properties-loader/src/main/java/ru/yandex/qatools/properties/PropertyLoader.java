package ru.yandex.qatools.properties;

import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.decorators.DefaultFieldDecorator;
import ru.yandex.qatools.properties.decorators.FieldDecorator;

import java.lang.reflect.Field;
import java.util.Properties;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

/**
 * User: eroshenkoam
 * Date: 11/13/12, 12:53 PM
 */
public final class PropertyLoader {

    private PropertyLoader() {
    }

    public static <T> void populate(T bean) {
        populate(bean, loadProperties(bean.getClass()));
    }

    public static <T> void populate(T bean, Properties properties) {
        populate(bean, new DefaultFieldDecorator(properties));
    }

    public static <T> void populate(T bean, FieldDecorator decorator) {
        Class<?> clazz = bean.getClass();
        while (clazz != Object.class) {
            initFields(decorator, bean, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private static void initFields(FieldDecorator decorator, Object bean, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Object value = decorator.decorate(field);
            if (value != null) {
                try {
                    field.setAccessible(true);
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Properties loadProperties(Class<?> clazz) {
        Properties result = new Properties();
        if (clazz.isAnnotationPresent(Resource.Classpath.class)) {
            String path = clazz.getAnnotation(Resource.Classpath.class).value();
            result.putAll(readProperties(ClassLoader.getSystemResourceAsStream(path)));
        }

        if (clazz.isAnnotationPresent(Resource.File.class)) {
            String path = clazz.getAnnotation(Resource.File.class).value();
            result.putAll(readProperties(new java.io.File(path)));
        }

        result.putAll(System.getProperties());
        return result;
    }


}
