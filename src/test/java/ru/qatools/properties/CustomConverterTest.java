package ru.qatools.properties;

import org.junit.Before;
import org.junit.Test;
import ru.qatools.properties.testdata.PropertiesWithCustomConverter;
import ru.qatools.properties.testdata.UpperCaseStringConverter;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:12 PM
 */
public class CustomConverterTest {

    private static final String VALUE = "value";

    private PropertiesWithCustomConverter properties;

    @Before
    public void initProperties() {
        Properties override = new Properties();
        override.setProperty("field", VALUE);

        properties = new PropertiesWithCustomConverter();
        PropertyLoader.newInstance()
                .withDefaults(override)
                .register(new UpperCaseStringConverter(), String.class)
                .populate(properties);
    }

    @Test
    public void testOutput() {
        assertThat(properties.field, equalTo(VALUE.toUpperCase()));
    }
}
