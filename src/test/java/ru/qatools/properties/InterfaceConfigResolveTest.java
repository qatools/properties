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

    @Test
    public void shouldSumPrefixesInSubConfigs() throws Exception {
        MyFirstConfig config = PropertyLoader.newInstance().populate(MyFirstConfig.class);
        MySecondConfig second = config.getConfig();

        assertThat(second.getProp(), is("my"));

        MyThirdConfig third = second.getConfig();
        assertThat(third.getProp(), is("my-sub"));
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

    @Resource.Classpath("sub.config.properties")
    public interface MyFirstConfig {

        @Config(prefix = "my")
        MySecondConfig getConfig();
    }

    public interface MySecondConfig {

        @Property("prop")
        String getProp();

        @Config(prefix = "sub")
        MyThirdConfig getConfig();
    }

    public interface MyThirdConfig {

        @Property("prop")
        String getProp();

    }
}
