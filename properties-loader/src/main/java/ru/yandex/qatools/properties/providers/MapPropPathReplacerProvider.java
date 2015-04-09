package ru.yandex.qatools.properties.providers;

import ru.yandex.qatools.properties.utils.PropsReplacer;

import java.util.Properties;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Kirill Merkushev lanwen@yandex-team.ru
 *         Date: 09.04.15
 */
public class MapPropPathReplacerProvider extends DefaultPropertyProvider {

    public static final String MAP_PROP_KEY_PATTERN = "\\$\\{map\\.([^\\}]*)\\}";

    protected Properties replacements;

    public MapPropPathReplacerProvider(Properties replacements) {
        this.replacements = replacements;
    }

    @Override
    public String[] filepath(Class<?> clazz) {
        return new PropsReplacer(super.filepath(clazz))
                .replaceProps(getReplacementPattern(), replacements)
                .getPathsAsArray();
    }

    @Override
    public String[] classpath(Class<?> clazz) {
        return new PropsReplacer(super.classpath(clazz))
                .replaceProps(getReplacementPattern(), replacements)
                .getPathsAsArray();
    }

    public String getReplacementPattern() {
        return MAP_PROP_KEY_PATTERN;
    }
}
