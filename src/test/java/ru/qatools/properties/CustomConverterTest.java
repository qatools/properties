package ru.qatools.properties;

import org.junit.Before;
import org.junit.Test;
import ru.qatools.properties.testdata.PropertiesWithCustomConverter;
import ru.qatools.properties.testdata.UpperCaseStringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 8:12 PM
 */
public class CustomConverterTest {

    private static final String VALUE_FOR_FIELD = "value";

    private static final String VALUE_FOR_FIELD_WITH_USE = "first, second";

    private PropertiesWithCustomConverter properties;

    @Before
    public void initProperties() {
        Properties override = new Properties();
        override.setProperty("field", VALUE_FOR_FIELD);
        override.setProperty("field.with.use.annotation", VALUE_FOR_FIELD_WITH_USE);

        properties = new PropertiesWithCustomConverter();
        PropertyLoader.newInstance()
                .withDefaults(override)
                .register(new UpperCaseStringConverter(), String.class)
                .populate(properties);
    }

    @Test
    public void testFieldWithOverriddenDefaultConverter() {
        assertThat(properties.field, equalTo(VALUE_FOR_FIELD.toUpperCase()));
    }

    @Test
    public void testFieldWithOverriddenConverterInUseAnnotation() {
        List<String> expected = Arrays.asList(VALUE_FOR_FIELD_WITH_USE.split(", "));
        assertThat(properties.fieldWithUseAnnotation, equalTo(expected));
    }
}
