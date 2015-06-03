package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class IntegerConverter implements Converter<Integer> {

    @Override
    public Integer convert(String from) throws Exception {
        return Integer.parseInt(from.trim());
    }
}
