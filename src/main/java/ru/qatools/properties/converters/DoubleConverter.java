package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class DoubleConverter implements Converter<Double> {

    @Override
    public Double convert(String from) throws Exception {
        return Double.parseDouble(from.trim());
    }
}
