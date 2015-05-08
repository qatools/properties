package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class LongConverter implements Converter<Long> {

    @Override
    public Long convert(String from) throws Exception {
        return Long.parseLong(from.trim());
    }
}
