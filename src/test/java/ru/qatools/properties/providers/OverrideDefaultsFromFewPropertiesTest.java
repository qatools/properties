package ru.qatools.properties.providers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qatools.properties.testdata.ProxyProperties;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 6:28 PM
 */
@RunWith(Parameterized.class)
public class OverrideDefaultsFromFewPropertiesTest {

    protected ProxyProperties proxy;
    protected Properties resources;

    public OverrideDefaultsFromFewPropertiesTest(ProxyProperties proxy, InputStream first, InputStream second)
            throws IOException {
        this.proxy = proxy;
        this.resources = new Properties();
        resources.load(first);
        resources.load(second);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws FileNotFoundException {
        return Arrays.asList(
                new Object[]{
                        ProxyPropertiesFactory.createProxyPropertiesWithFewFilesAnnotation(),
                        new FileInputStream(new File(ProxyPropertiesFactory.FILE_PATH)),
                        new FileInputStream(new File(ProxyPropertiesFactory.FILE_LOGIN_PATH))
                },
                new Object[]{
                        ProxyPropertiesFactory.createProxyPropertiesWithFewResourceAnnotation(),
                        ClassLoader.getSystemResourceAsStream(ProxyPropertiesFactory.RESOURCE_PATH),
                        ClassLoader.getSystemResourceAsStream(ProxyPropertiesFactory.RESOURCE_LOGIN_PATH)
                }
        );
    }

    @Test
    public void shouldInitProxyFromResource() {
        assertThat(proxy.getHost(), equalTo(
                resources.getProperty(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY)
        ));
        assertThat(proxy.getPort(), equalTo(Integer.parseInt(
                resources.getProperty(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY)
        )));
        assertThat(proxy.isActive(), equalTo(Boolean.parseBoolean(
                resources.getProperty(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY)
        )));
        assertThat(proxy.getLogin(), equalTo(
                resources.getProperty(ProxyPropertiesFactory.PROXY_LOGIN_PROPERTY_KEY)
        ));
        assertThat(proxy.getPass(), equalTo(
                resources.getProperty(ProxyPropertiesFactory.PROXY_PASSWORD_PROPERTY_KEY)
        ));
    }

}
