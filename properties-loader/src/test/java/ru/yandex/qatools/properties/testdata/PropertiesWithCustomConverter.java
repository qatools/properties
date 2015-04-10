package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:14 PM
 */
public class PropertiesWithCustomConverter {

    @Property("field")
    public String field;
}
