package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.Use;

import java.util.Properties;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:14 PM
 */
@Resource.Classpath("${proeprty.load}.property")
public class PropertiesWithCustomConverter {


    @Property("field")
    @Use(UpperCaseStringConverter.class)
    public String field;
}
