package ru.qatools.properties;

import org.junit.Test;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 03.06.15
 */
public class InterfaceConfigResolveTest {

    @Test
    public void shouldResolveSubConfig() throws Exception {
        MyConfig config = PropertyLoader.newInstance().populate(MyConfig.class);

        assertThat(config, notNullValue());
        assertThat(config.getHost(), is("localhost"));

        assertThat(config.getConfig(), notNullValue());
        assertThat(config.getConfig().getPort(), is(9000));
    }

    @Test(expected = PropertyLoaderException.class)
    public void shouldResolveRecursiveConfig() throws Exception {
        PropertyLoader.newInstance().populate(MyRecursiveConfig.class);
    }

    @Resource.Classpath(ProxyPropertiesFactory.RESOURCE_PATH)
    public interface MyConfig {

        @Property("proxy.host")
        String getHost();

        @Config(prefix = "proxy")
        MySubConfig getConfig();
    }

    public interface MySubConfig {

        @Property("port")
        int getPort();

    }

    @Resource.Classpath(ProxyPropertiesFactory.RESOURCE_PATH)
    public interface MyRecursiveConfig {

        @Config(prefix = "recursive")
        MyRecursiveConfig getConfig();
    }
}
