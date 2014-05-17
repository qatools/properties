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

    @Property(ProxyPropertiesFactory.PROXY_LOGIN_PROPERTY_KEY)
    protected String login = "default-login";

    @Property(ProxyPropertiesFactory.PROXY_PASSWORD_PROPERTY_KEY)
    protected String pass = "default-pass";

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public boolean isActive() {
        return active;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }
}
