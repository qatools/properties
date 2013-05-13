package ru.yandex.qatools.properties;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.PropertiesWithUnsupportedTypes;

import java.util.Properties;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 7:29 PM
 */
public class UnsupportedTypesTest {

    private Properties override = new Properties();
    private PropertiesWithUnsupportedTypes properties = new PropertiesWithUnsupportedTypes();

    @Before
    public void initProperties() {
        override.setProperty("unsupported", "value");
        PropertyLoader.populate(this, override);
    }


    @Test
    public void unsupportedTypeFiledMustBeNullAfterInitFromFile() {
        assertThat(properties.getSupportedTypesProperties(), nullValue());
    }
}
