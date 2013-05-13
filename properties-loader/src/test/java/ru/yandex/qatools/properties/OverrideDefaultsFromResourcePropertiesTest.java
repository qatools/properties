package ru.yandex.qatools.properties;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.ProxyProperties;
import ru.yandex.qatools.properties.testdata.ProxyPropertiesFactory;

import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 8:23 PM
 */
public class OverrideDefaultsFromResourcePropertiesTest {

    protected static ProxyProperties proxy;
    protected static Properties resources;

    @BeforeClass
    public static void init() throws Exception {
        proxy = ProxyPropertiesFactory.createProxyPropertiesWithResourceAnnotation();
        resources = new Properties();
        resources.load(ClassLoader.getSystemResourceAsStream(ProxyPropertiesFactory.RESOURCE_PATH));
    }


    @Test
    public void proxyMustBeInitedFromResource() {
        assertThat(proxy.getHost(), equalTo((
                resources.getProperty(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY)))
        );
        assertThat(proxy.getPort(), equalTo(Integer.parseInt(
                resources.getProperty(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY)))
        );
        assertThat(proxy.isActive(), equalTo(Boolean.parseBoolean(
                resources.getProperty(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY)))
        );
    }

}
