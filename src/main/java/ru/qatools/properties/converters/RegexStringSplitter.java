package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class RegexStringSplitter implements StringSplitter {

    private String regex;

    public RegexStringSplitter(String regex) {
        this.regex = regex;
    }

    @Override
    public String[] split(String string) {
        return string.split(regex);
    }
}
