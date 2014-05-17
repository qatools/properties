package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.With;

import java.util.Properties;

/**
 * @author artkoshelev
 */
@Resource.Classpath("${map.file.name}.path.${map.scope.value}.properties")
@With(MapPropPathReplacerProvider.class)
public class UseMapReplacerProviderProperty {

    public UseMapReplacerProviderProperty(String name, String scope) {
        Properties map = new Properties();
        map.put("file.name", name);
        map.put("scope.value", scope);
        PropertyLoader.populate(this, map);
    }

    @Property("property")
    private String property = "undefined";

    public String getProperty() {
        return property;
    }
}