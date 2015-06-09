package ru.qatools.properties;

import org.junit.Test;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 03.06.15
 */
public class RequiredPropertyTest {

    @Test(expected = PropertyLoaderException.class)
    public void shouldThrowAnExceptionIfRequiredDoesNotExists() throws Exception {
        new PropertyLoader().populate(MyFirstConfig.class);
    }

    @Test
    public void shouldNotThrowAnExceptionIfRequiredExists() throws Exception {
        new PropertyLoader().populate(MySecondConfig.class);
    }

    public interface MyFirstConfig {

        @Required
        @Property("proxy.host")
        String getHost();

    }

    @Resource.Classpath(ProxyPropertiesFactory.RESOURCE_PATH)
    public interface MySecondConfig {

        @Required
        @Property("proxy.host")
        String getHost();

    }
}
