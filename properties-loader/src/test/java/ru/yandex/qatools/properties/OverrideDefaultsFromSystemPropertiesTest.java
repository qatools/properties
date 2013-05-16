package ru.yandex.qatools.properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.yandex.qatools.properties.testdata.ProxyProperties;
import ru.yandex.qatools.properties.testdata.ProxyPropertiesFactory;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/24/13, 12:33 AM
 */
public class OverrideDefaultsFromSystemPropertiesTest {

    protected static ProxyProperties proxy;

    public static final String PROXY_HOST_VALUE = "sys.proxy.yandex.net";

    public static final String PROXY_PORT_VALUE = "3135";

    public static final String PROXY_ACTIVE_VALUE = "false";

    @BeforeClass
    public static void init() throws Exception {
        System.setProperty(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY, PROXY_HOST_VALUE);
        System.setProperty(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY, PROXY_PORT_VALUE);
        System.setProperty(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY, PROXY_ACTIVE_VALUE);
        proxy = ProxyPropertiesFactory.createProxyPropertiesWithSystemOverride();
    }


    @Test
    public void proxyMustBeInitedFromResource() {
        assertThat(proxy.getHost(), equalTo((
                System.getProperty(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY)))
        );
        assertThat(proxy.getPort(), equalTo(Integer.parseInt(
                System.getProperty(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY)))
        );
        assertThat(proxy.isActive(), equalTo(Boolean.parseBoolean(
                System.getProperty(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY)))
        );
    }

    @AfterClass
    public static void clearSystemProperties() {
        System.clearProperty(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY);
        System.clearProperty(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY);
        System.clearProperty(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY);
    }
}
