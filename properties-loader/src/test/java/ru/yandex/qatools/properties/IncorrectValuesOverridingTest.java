package ru.yandex.qatools.properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.ProxyProperties;
import ru.yandex.qatools.properties.testdata.ProxyPropertiesFactory;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 4:38 PM
 */
public class IncorrectValuesOverridingTest {

    protected ProxyProperties defaultsProperties = ProxyPropertiesFactory.createProxyProperties();

    protected ProxyProperties overriddenProperties;

    @Before
    public void setupSystemProperties() {
        System.setProperty("proxy.port", "aaa");
    }

    @Test
    public void checkIncorrectValueDontOverrideDefaultValue() {
        overriddenProperties = ProxyPropertiesFactory.createProxyPropertiesWithSystemOverride();
        assertThat(overriddenProperties.getPort(), equalTo(defaultsProperties.getPort()));
    }

    @After
    public void clearSystemProperties() {
        System.clearProperty("proxy.port");
    }

}
