package ru.qatools.properties.testdata;

import ru.qatools.properties.Property;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 7:27 PM
 */
public class PropertiesWithUnsupportedTypes {

    @Property("unsupported")
    private PropertiesWithUnsupportedTypes supportedTypesProperties;


    public PropertiesWithUnsupportedTypes getSupportedTypesProperties() {
        return supportedTypesProperties;
    }
}
