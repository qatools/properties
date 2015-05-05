package ru.qatools.properties.testdata;

import ru.qatools.properties.annotations.Property;
import ru.qatools.properties.annotations.Use;

import java.util.List;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:14 PM
 */
public class PropertiesWithCustomConverter {

    @Property("field")
    public String field;

    @Use(CommaSeparatedStringConverter.class)
    @Property("field.with.use.annotation")
    public List<String> fieldWithUseAnnotation;
}
