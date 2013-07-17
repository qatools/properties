package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Resource;
import ru.yandex.qatools.properties.annotations.With;

import java.util.Properties;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

/**
 * 
 * @author artkoshelev
 * 
 */
@Resource.Classpath("${system.file.name}.path.${system.scope.value}.properties")
@With(SyspropPathReplacerProvider.class)
public class UseSystemReplacerProviderProperty {

	public UseSystemReplacerProviderProperty() {
		PropertyLoader.populate(this);
	}

	@Property("property")
	private String property = "undefined";

	public String getProperty() {
        return property;
	}
}