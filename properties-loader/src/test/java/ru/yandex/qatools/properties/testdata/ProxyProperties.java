package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.annotations.Property;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 4:25 PM
 */
public class ProxyProperties {

    @Property(ProxyPropertiesFactory.PROXY_HOST_PROPERTY_KEY)
    protected String host = "proxy.yandex.ru";

    @Property(ProxyPropertiesFactory.PROXY_PORT_PROPERTY_KEY)
    protected int port = 80;

    @Property(ProxyPropertiesFactory.PROXY_ACTIVE_PROPERTY_KEY)
    protected boolean active = false;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isActive() {
        return active;
    }
}
