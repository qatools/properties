package ru.qatools.properties.testdata;

import static ru.qatools.properties.utils.PropertiesUtils.readProperties;

import java.util.Properties;

import ru.qatools.properties.annotations.Property;
import ru.qatools.properties.PropertyLoader;

/**
 * 
 * @author artkoshelev
 * 
 */
public class MultiFileProperty {

	public MultiFileProperty() {
		PropertyLoader.newInstance()
				.withDefaults(loadProperties())
				.populate(this);
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