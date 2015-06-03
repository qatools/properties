package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class FloatConverter implements Converter<Float> {

    @Override
    public Float convert(String from) throws Exception {
        return Float.parseFloat(from.trim());
    }
}
