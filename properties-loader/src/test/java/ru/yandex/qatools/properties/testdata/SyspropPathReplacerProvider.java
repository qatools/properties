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
public class SyspropPathReplacerProvider extends DefaultPropertyProvider {
    public static final String SYS_PROP_KEY_PATTERN = "\\$\\{system\\.([^\\}]*)\\}";

    @Override
    public String filepath(Class<?> clazz, Properties properties) {
        return replaceWithSystemProps(super.filepath(clazz, properties));
    }

    @Override
    public String classpath(Class<?> clazz, Properties properties) {
        return replaceWithSystemProps(super.classpath(clazz, properties));
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
