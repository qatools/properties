package ru.qatools.properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import ru.qatools.properties.testdata.ProxyProperties;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 6:28 PM
 */
public class OverrideDefaultsFromFilePropertiesTest {

    protected static ProxyProperties proxy;
    protected static Properties resources;

    @BeforeClass
    public static void init() throws Exception {
        proxy = ProxyPropertiesFactory.createProxyPropertiesWithFileAnnotation();
        resources = new Properties();
        resources.load(new FileReader(new File(ProxyPropertiesFactory.FILE_PATH)));
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
