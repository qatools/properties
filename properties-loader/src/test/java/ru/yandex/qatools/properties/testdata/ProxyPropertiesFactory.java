package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.PropertyLoader;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 8:24 PM
 */
public class ProxyPropertiesFactory {

    public static final String RESOURCE_PATH = "proxy.properties";

    public static final String RESOURCE_LOGIN_PATH = "proxy.login.properties";

    public static final String FILE_PATH = "src/test/resources/proxy.properties";

    public static final String FILE_LOGIN_PATH = "src/test/resources/proxy.login.properties";

    public static final String PROXY_HOST_PROPERTY_KEY = "proxy.host";

    public static final String PROXY_PORT_PROPERTY_KEY = "proxy.port";

    public static final String PROXY_ACTIVE_PROPERTY_KEY = "proxy.active";

    public static final String PROXY_LOGIN_PROPERTY_KEY = "proxy.login";

    public static final String PROXY_PASSWORD_PROPERTY_KEY = "proxy.password";

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

    public static ProxyProperties createProxyPropertiesWithFewResourceAnnotation() {
        ProxyProperties properties = new ProxyPropertiesWithFewResourceAnnotation();
        PropertyLoader.populate(properties);
        return properties;
    }

    public static ProxyProperties createProxyPropertiesWithFileAnnotation() {
        ProxyProperties properties = new ProxyPropertiesWithFileAnnotation();
        PropertyLoader.populate(properties);
        return properties;
    }

    public static ProxyProperties createProxyPropertiesWithFewFilesAnnotation() {
        ProxyProperties properties = new ProxyPropertiesWithFewFileAnnotation();
        PropertyLoader.populate(properties);
        return properties;
    }
}
