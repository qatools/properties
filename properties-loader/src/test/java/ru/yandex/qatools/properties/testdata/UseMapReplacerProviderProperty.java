package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.providers.MapPropPathReplacerProvider;

import java.util.Properties;

/**
 * @author artkoshelev
 */
@Resource.Classpath("${map.file.name}.path.${map.scope.value}.properties")
public class UseMapReplacerProviderProperty {

    public UseMapReplacerProviderProperty(String name, String scope) {
        Properties map = new Properties();
        map.put("file.name", name);
        map.put("scope.value", scope);
        PropertyLoader.newInstance()
                .withPropertyProvider(new MapPropPathReplacerProvider(map))
                .populate(this);
    }

    @Property("property")
    private String property = "undefined";

    public String getProperty() {
        return property;
    }
}