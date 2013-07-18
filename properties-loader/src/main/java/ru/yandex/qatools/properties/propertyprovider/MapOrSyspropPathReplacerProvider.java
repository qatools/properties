package ru.yandex.qatools.properties.propertyprovider;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 19:58
 */
public class MapOrSyspropPathReplacerProvider extends DefaultPropertyProvider {
    public static final String MAP_PROP_KEY_PATTERN = "\\$\\{map\\.([^\\}]*)\\}";
    public static final String SYS_PROP_KEY_PATTERN = "\\$\\{system\\.([^\\}]*)\\}";

    @Override
    public String filepath(Class<?> clazz, Properties properties) {
        String filepath = super.filepath(clazz, properties);
        String mapreplaced = replaceWithMapProps(filepath, properties);
        return replaceWithSystemProps(mapreplaced);
    }

    @Override
    public String classpath(Class<?> clazz, Properties properties) {
        String classpath = super.classpath(clazz, properties);
        String mapreplaced = replaceWithMapProps(classpath, properties);
        return replaceWithSystemProps(mapreplaced);
    }

    private String replaceWithMapProps(String path, Properties properties) {
        Matcher matcher = Pattern.compile(MAP_PROP_KEY_PATTERN).matcher(path);
        String replaced = path;
        while (matcher.find()) {
            replaced = replaced.replace(matcher.group(0), properties.getProperty(matcher.group(1), ""));
        }
        return replaced;
    }

    private String replaceWithSystemProps(String path) {
        Matcher matcher = Pattern.compile(SYS_PROP_KEY_PATTERN).matcher(path);
        String replaced = path;
        while (matcher.find()) {
            replaced = replaced.replace(matcher.group(0), System.getProperty(matcher.group(1), ""));
        }
        return replaced;
    }
}
