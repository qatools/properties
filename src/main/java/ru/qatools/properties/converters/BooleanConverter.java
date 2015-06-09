package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Boolean convert(String from) throws Exception {
        if ("true".equalsIgnoreCase(from.trim())) {
            return true;
        }
        if ("false".equalsIgnoreCase(from.trim())) {
            return false;
        }
        throw new ConversionException(String.format(
                "Could not convert value <%s> to boolean", from));
    }
}
