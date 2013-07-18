package ru.yandex.qatools.properties.testdata;

import static ru.yandex.qatools.properties.utils.PropertiesUtils.readProperties;

import java.util.Properties;

import ru.yandex.qatools.properties.PropertyLoader;
import ru.yandex.qatools.properties.annotations.Property;

/**
 * 
 * @author artkoshelev
 * 
 */
public class MultiFileProperty {

	public MultiFileProperty() {
		PropertyLoader.populate(this, loadProperties());
	}

	@Property("property")
	private String property = "undefined";

	public String getProperty() {
        return property;
	}

	private static Properties loadProperties() {
		Properties result = new Properties();
		String filePath = System.getProperty("property.file");
        if (filePath != null) {
			result.putAll(readProperties(ClassLoader.getSystemResourceAsStream(filePath)));
		}
        return result;
	}
}