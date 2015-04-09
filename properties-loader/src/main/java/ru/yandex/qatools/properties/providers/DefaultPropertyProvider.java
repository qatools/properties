package ru.yandex.qatools.properties.providers;

import ru.yandex.qatools.properties.annotations.Resource;

import java.util.Properties;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

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
            properties.putAll(readProperties(classLoader.getResourceAsStream(path)));
        }

        for (String path : filepath(clazz)) {
            properties.putAll(readProperties(new java.io.File(path)));
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
