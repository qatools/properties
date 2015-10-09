package ru.qatools.properties.providers;

import ru.qatools.properties.Resource;
import ru.qatools.properties.utils.PropertiesUtils;

import java.io.File;
import java.util.Properties;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Kirill Merkushev lanwen@yandex-team.ru
 *         Date: 09.04.15
 */
public class DefaultPropertyProvider extends SystemPropertyProvider {

    @Override
    public Properties provide(ClassLoader classLoader, Class<?> beanClass) {
        Properties properties = new Properties();

        for (String path : classpath(beanClass)) {
            properties.putAll(PropertiesUtils.readProperties(classLoader.getResourceAsStream(path)));
        }

        Properties systemProperties = System.getProperties();
        for (String path : filepath(beanClass)) {
            path = PropertiesUtils.injectProperties(path, systemProperties);
            properties.putAll(PropertiesUtils.readProperties(new File(path)));
        }

        properties.putAll(super.provide(classLoader, beanClass));
        return properties;
    }

    protected String[] filepath(Class<?> clazz) {
        Resource.File annotation = clazz.getAnnotation(Resource.File.class);
        return annotation == null ? new String[]{} : annotation.value();
    }

    protected String[] classpath(Class<?> clazz) {
        Resource.Classpath annotation = clazz.getAnnotation(Resource.Classpath.class);
        return annotation == null ? new String[]{} : annotation.value();
    }
}
