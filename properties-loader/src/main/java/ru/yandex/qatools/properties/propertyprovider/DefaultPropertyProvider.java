package ru.yandex.qatools.properties.propertyprovider;

import ru.yandex.qatools.properties.annotations.Resource;

import java.lang.annotation.Annotation;
import java.util.Properties;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;


/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 17:28
 */
public class DefaultPropertyProvider implements PropertyProvider {
    @Override
    public <T> Properties provide(T bean, Properties properties) {
        Class<?> clazz = bean.getClass();

        if (have(clazz, Resource.Classpath.class)) {
            String path = classpath(clazz, properties);
            properties.putAll(readProperties(ClassLoader.getSystemResourceAsStream(path)));
        }

        if (have(clazz, Resource.File.class)) {
            String path = filepath(clazz, properties);
            properties.putAll(readProperties(new java.io.File(path)));
        }

        properties.putAll(System.getProperties());
        return properties;
    }


    protected boolean have(Class<?> clazz, Class<? extends Annotation> anno) {
        return clazz.isAnnotationPresent(anno);
    }

    protected String filepath(Class<?> clazz, Properties properties) {
        return clazz.getAnnotation(Resource.File.class).value();
    }

    protected String classpath(Class<?> clazz, Properties properties) {
        return clazz.getAnnotation(Resource.Classpath.class).value();
    }
}
