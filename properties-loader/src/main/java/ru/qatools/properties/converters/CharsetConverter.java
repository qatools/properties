package ru.qatools.properties.converters;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.nio.charset.Charset;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 26.03.15
 */
public class CharsetConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (Charset.class.equals(type)) {
            return type.cast(Charset.forName(value.toString()));
        }

        throw conversionException(type, value);
    }

    @Override
    protected Class<Charset> getDefaultType() {
        return Charset.class;
    }
}
