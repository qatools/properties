package ru.yandex.qatools.properties;

import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.With;
import ru.yandex.qatools.properties.loadhelper.MapOrSyspropPathReplacerProvider;

import java.util.Properties;

/**
 * @author artkoshelev
 */
@Resource.Classpath("${system.file.name}.path.${map.scope.value}.properties")
@With(MapOrSyspropPathReplacerProvider.class)
public class UseMapOrSyspropReplacerProviderProperty {

    public UseMapOrSyspropReplacerProviderProperty(String scope) {
        Properties map = new Properties();
        map.put("scope.value", scope);
        PropertyLoader.populate(this, map);
    }

    @Property("property")
    private String property = "undefined";

    public String getProperty() {
        return property;
    }
}