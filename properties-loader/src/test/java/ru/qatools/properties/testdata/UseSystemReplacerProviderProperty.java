package ru.qatools.properties.testdata;

import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.annotations.Property;
import ru.qatools.properties.annotations.Resource;
import ru.qatools.properties.providers.SysPropPathReplacerProvider;

/**
 * @author artkoshelev
 */
@Resource.Classpath("${system.file.name}.path.${system.scope.value}.properties")
public class UseSystemReplacerProviderProperty {

    public UseSystemReplacerProviderProperty() {
        PropertyLoader.newInstance()
                .withPropertyProvider(new SysPropPathReplacerProvider())
                .populate(this);
    }

    @Property("property")
    private String property = "undefined";

    public String getProperty() {
        return property;
    }
}