package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class ShortConverter implements Converter<Short> {

    @Override
    public Short convert(String from) throws Exception {
        return Short.parseShort(from.trim());
    }
}
