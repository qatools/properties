package ru.qatools.properties.providers;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 09.04.15
 */
public class SysPropPathReplacerProvider extends MapPropPathReplacerProvider {

    public static final String SYS_PROP_KEY_PATTERN = "\\$\\{system\\.([^\\}]*)\\}";

    public SysPropPathReplacerProvider() {
        super(System.getProperties());
    }

    @Override
    public String getReplacementPattern() {
        return SYS_PROP_KEY_PATTERN;
    }
}
