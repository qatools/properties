package ru.qatools.properties;

import org.junit.Before;
import org.junit.Test;
import ru.qatools.properties.testdata.LowerCaseStringConverter;
import ru.qatools.properties.testdata.PropertiesWithCustomConverter;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:12 PM
 */
public class CustomConverterTest {

    private static final String VALUE = "Value";

    private PropertiesWithCustomConverter properties;

    @Before
    public void initProperties() {
        Properties override = new Properties();
        override.setProperty("field", VALUE);
        override.setProperty("field.with.use.annotation", VALUE);

        properties = new PropertiesWithCustomConverter();
        PropertyLoader.newInstance()
                .withDefaults(override)
                .register(new LowerCaseStringConverter(), String.class)
                .populate(properties);
    }

    @Test
    public void testFieldWithOverriddenDefaultConverter() {
        assertThat(properties.field, equalTo(VALUE.toLowerCase()));
    }

    @Test
    public void testFieldWithOverriddenConverterInUseAnnotation() {
        assertThat(properties.fieldWithUseAnnotation, equalTo(VALUE.toUpperCase()));
    }
}