package ru.yandex.qatools.properties.providers;

import ru.yandex.qatools.properties.utils.PropsReplacer;

import java.util.Properties;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 19:58
 */
public class MapOrSyspropPathReplacerProvider extends DefaultPropertyProvider {

    public static final String MAP_PROP_KEY_PATTERN = "\\$\\{map\\.([^\\}]*)\\}";

    public static final String SYS_PROP_KEY_PATTERN = "\\$\\{system\\.([^\\}]*)\\}";

    @Override
    public String[] filepath(Class<?> clazz, Properties properties) {
        return new PropsReplacer(super.filepath(clazz, properties))
                .replaceProps(MAP_PROP_KEY_PATTERN, properties)
                .replaceProps(SYS_PROP_KEY_PATTERN, System.getProperties())
                .getPathsAsArray();
    }

    @Override
    public String[] classpath(Class<?> clazz, Properties properties) {
        return new PropsReplacer(super.classpath(clazz, properties))
                .replaceProps(MAP_PROP_KEY_PATTERN, properties)
                .replaceProps(SYS_PROP_KEY_PATTERN, System.getProperties())
                .getPathsAsArray();
    }

}
