package ru.yandex.qatools.properties.converters;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.net.URI;
import java.net.URL;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 2:44 PM
 */
public class URIConverter extends AbstractConverter {

    @Override
    protected <T> T convertToType(Class<T> type, Object value) throws Throwable {
        if (URI.class.equals(type)) {
            return type.cast(URI.create(value.toString()));
        }

        throw conversionException(type, value);
    }

    @Override
    protected Class<URI> getDefaultType() {
        return URI.class;
    }
}
