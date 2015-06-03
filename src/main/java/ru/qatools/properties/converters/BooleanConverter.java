package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Boolean convert(String from) throws Exception {
        return Boolean.parseBoolean(from.trim());
    }
}
