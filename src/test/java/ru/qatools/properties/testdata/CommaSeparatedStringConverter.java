package ru.qatools.properties.testdata;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 05.05.15
 */
public class CommaSeparatedStringConverter extends AbstractConverter {
    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        return type.cast(Arrays.asList(value.toString().split(", ")));
    }

    @Override
    protected Class<?> getDefaultType() {
        return List.class;
    }
}
