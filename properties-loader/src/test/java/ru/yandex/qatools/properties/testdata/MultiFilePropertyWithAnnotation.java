package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;

/**
 * @author lanwen
 */
@Resource.ClasspathInProperty("property.file")
public class MultiFilePropertyWithAnnotation {

    public MultiFilePropertyWithAnnotation() {
        PropertyLoader.populate(this);
    }

    @Property("property")
    private String property = "undefined";

    public String getProperty() {
        return property;
    }
}