package ru.qatools.properties.testdata;

import org.apache.commons.beanutils.Converter;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 11:07 PM
 */
public class UpperCaseStringConverter implements Converter {

    @Override
    public Object convert(Class aClass, Object o) {
        return o.toString().toUpperCase();
    }
}
