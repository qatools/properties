package ru.yandex.qatools.properties.propertyprovider;

import java.util.Properties;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 15:45
 */
public interface PropertyProvider {
    <T> Properties provide(T bean, Properties properties);
}
