package ru.yandex.qatools.properties.converters;

import org.apache.commons.beanutils.converters.AbstractConverter;

import java.net.URI;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 2:44 PM
 */
public class URIConverter extends AbstractConverter {

    @Override
    protected Object convertToType(Class aClass, Object o) {
        return URI.create(o.toString());
    }

    @Override
    protected Class getDefaultType() {
        return URI.class;
    }
}
