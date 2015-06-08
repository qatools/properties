package ru.qatools.properties;

import org.junit.Test;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 03.06.15
 */
public class InterfaceSupportTest {

    @Test
    public void shouldCreateProxy() throws Exception {
        MyExtendedConfig config = new PropertyLoader().populate(MyExtendedConfig.class);

        assertThat(config.getHost(), is("localhost"));
        assertThat(config.getPort(), is(9000));
        assertThat(config.getIntSomething(), is(0));
        assertThat(config.getIntegerSomething(), nullValue());
    }

    public interface MyConfig {

        @Property("proxy.host")
        String getHost();

    }

    @Resource.Classpath(ProxyPropertiesFactory.RESOURCE_PATH)
    public interface MyExtendedConfig extends MyConfig {

        @Property("proxy.port")
        int getPort();

        @Property("property-doesn't-exists")
        int getIntSomething();

        @Property("property-doesn't-exists")
        Integer getIntegerSomething();
    }
}
