package ru.yandex.qatools.properties.converters;

import org.apache.commons.beanutils.Converter;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 3:01 PM
 */
public class EnumConverter implements Converter {

    @Override
    public Object convert(Class aClass, Object o) {
        return Enum.valueOf(aClass, o.toString().toUpperCase().trim());
    }
}
