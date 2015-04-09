package ru.yandex.qatools.properties;

import org.junit.Test;
import ru.yandex.qatools.properties.testdata.PropertiesWithAbsenceFile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         4/17/13, 2:31 PM
 */
public class AbsenceResourcePropertiesTest {

    PropertiesWithAbsenceFile properties = new PropertiesWithAbsenceFile();

    @Test
    public void propertiesWithAbsenceResourceFileShouldBeSame() {
        String property = properties.getProperty();
        PropertyLoader.newInstance().populate(properties);
        assertThat("Properties after init absence file", property, equalTo(properties.getProperty()));
    }
}
