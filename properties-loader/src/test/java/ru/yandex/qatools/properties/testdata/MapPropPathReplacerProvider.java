package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.providers.DefaultPropertyProvider;
import ru.yandex.qatools.properties.utils.PropsReplacer;

import java.util.Properties;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 19:58
 */
public class MapPropPathReplacerProvider extends DefaultPropertyProvider {
    public static final String MAP_PROP_KEY_PATTERN = "\\$\\{map\\.([^\\}]*)\\}";

    @Override
    public String[] filepath(Class<?> clazz, Properties properties) {
        return new PropsReplacer(super.filepath(clazz, properties))
                .replaceProps(MAP_PROP_KEY_PATTERN, properties)
                .getPathsAsArray();
    }

    @Override
    public String[] classpath(Class<?> clazz, Properties properties) {
        return new PropsReplacer(super.classpath(clazz, properties))
                .replaceProps(MAP_PROP_KEY_PATTERN, properties)
                .getPathsAsArray();
    }
}
