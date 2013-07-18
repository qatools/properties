package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.providers.DefaultPropertyProvider;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 19:58
 */
public class MapPathReplacerProvider extends DefaultPropertyProvider {
    public static final String MAP_PROP_KEY_PATTERN = "\\$\\{map\\.([^\\}]*)\\}";

    @Override
    public String filepath(Class<?> clazz, Properties properties) {
        return replaceWithMapProps(super.filepath(clazz, properties), properties);
    }

    @Override
    public String classpath(Class<?> clazz, Properties properties) {
        return replaceWithMapProps(super.classpath(clazz, properties), properties);
    }


    private String replaceWithMapProps(String path, Properties properties) {
        Matcher matcher = Pattern.compile(MAP_PROP_KEY_PATTERN).matcher(path);
        String replaced = path;
        while (matcher.find()) {
            replaced = replaced.replace(matcher.group(0), properties.getProperty(matcher.group(1), ""));
        }
        return replaced;
    }

}
