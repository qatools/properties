package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class StringConverter implements Converter<String> {
    @Override
    public String convert(String from) throws Exception {
        return from;
    }
}
