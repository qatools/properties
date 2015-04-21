package ru.qatools.properties.providers;

import ru.qatools.properties.annotations.Resource;
import ru.qatools.properties.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Kirill Merkushev lanwen@yandex-team.ru
 *         Date: 09.04.15
 */
public class DefaultPropertyProvider extends SystemPropertyProvider {

    @Override
    public Properties provide(ClassLoader classLoader, Object bean) {
        Properties properties = super.provide(classLoader, bean);

        Class<?> clazz = bean.getClass();

        for (String path : classpath(clazz)) {
            properties.putAll(PropertiesUtils.readProperties(classLoader.getResourceAsStream(path)));
        }

        for (String path : filepath(clazz)) {
            properties.putAll(PropertiesUtils.readProperties(new java.io.File(path)));
        }

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
