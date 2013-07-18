package ru.yandex.qatools.properties;

import ru.yandex.qatools.properties.annotations.With;
import ru.yandex.qatools.properties.decorators.DefaultFieldDecorator;
import ru.yandex.qatools.properties.decorators.FieldDecorator;
import ru.yandex.qatools.properties.exeptions.PropertyLoaderException;
import ru.yandex.qatools.properties.providers.DefaultPropertyProvider;
import ru.yandex.qatools.properties.providers.PropertyProvider;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * User: eroshenkoam
 * Date: 11/13/12, 12:53 PM
 */
public final class PropertyLoader {

    private PropertyLoader() {
    }

    public static <T> void populate(T bean) {
        populate(bean, new Properties());
    }

    public static <T> void populate(T bean, Properties properties) {
        PropertyProvider provider = instantiatePropertyProvider(bean);
        Properties completed = provider.provide(bean, properties);
        populate(bean, new DefaultFieldDecorator(completed));
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
                    throw new PropertyLoaderException(
                            String.format("Can not set bean <%s> field <%s> value", bean, field),
                            e);
                }
            }
        }
    }

    private static <T> PropertyProvider instantiatePropertyProvider(T bean) {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(With.class)) {
            try {
                return clazz.getAnnotation(With.class).value().newInstance();
            } catch (InstantiationException e) {
                throw new PropertyLoaderException("Can't create instance property provider in class "
                        + bean.getClass().getName(), e);
            } catch (IllegalAccessException e) {
                throw new PropertyLoaderException("Can't load property provider in class "
                        + bean.getClass().getName(), e);
            }
        }
        return new DefaultPropertyProvider();
    }


}
