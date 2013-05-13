package ru.yandex.qatools.properties;

import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;
import org.junit.BeforeClass;
import ru.yandex.qatools.properties.testdata.ProxyProperties;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 3:44 PM
 */
public class DefaultValuesPropertiesTest {

    public static ProxyProperties defaultProperties;

    public static ProxyProperties overriddenProperties;

    @BeforeClass
    public static void initDefaultProperties() {
        defaultProperties = new ProxyProperties();
    }

    @BeforeClass
    public static void initOverrideProperties() {
        overriddenProperties = new ProxyProperties();
        PropertyLoader.populate(overriddenProperties);
    }

    @Test
    public void testOutput() {
        System.out.println(ConvertUtils.convert(new String[]{"abc", "deg", "efg"}, String.class));
    }

    @Test
    public void defaultValuesWillNotOverrideByEmptySystemProperties() {
        assertThat(defaultProperties.getHost(), equalTo(overriddenProperties.getHost()));
        assertThat(defaultProperties.getPort(), equalTo(overriddenProperties.getPort()));
        assertThat(defaultProperties.isActive(), equalTo(overriddenProperties.isActive()));
    }
}
