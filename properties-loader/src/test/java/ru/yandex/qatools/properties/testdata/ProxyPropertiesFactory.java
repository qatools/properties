package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 8:24 PM
 */
public class ProxyPropertiesFactory {

    public static final String RESOURCE_PATH = "proxy.properties";

    public static final String FILE_PATH = "src/test/resources/proxy.properties";

    public static final String PROXY_HOST_PROPERTY_KEY = "proxy.host";

    public static final String PROXY_PORT_PROPERTY_KEY = "proxy.port";

    public static final String PROXY_ACTIVE_PROPERTY_KEY = "proxy.active";

    public static ProxyProperties createProxyProperties() {
        return new ProxyProperties();
    }

    public static ProxyProperties createProxyPropertiesWithSystemOverride() {
        ProxyProperties properties = new ProxyProperties();
        PropertyLoader.populate(properties);
        return properties;
    }

    public static ProxyProperties createProxyPropertiesWithResourceAnnotation() {
        ProxyProperties properties = new ProxyPropertiesWithResourceAnnotation();
        PropertyLoader.populate(properties);
        return properties;
    }

    public static ProxyProperties createProxyPropertiesWithFileAnnotation() {
        ProxyProperties properties = new ProxyPropertiesWithFileAnnotation();
        PropertyLoader.populate(properties);
        return properties;
    }
}
