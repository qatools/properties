package ru.qatools.properties.providers;

import java.util.Properties;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 09.04.15
 */
public class SystemPropertyProvider implements PropertyProvider {

    @Override
    public Properties provide(ClassLoader classLoader, Class<?> beanClass) {
        return System.getProperties();
    }
}
