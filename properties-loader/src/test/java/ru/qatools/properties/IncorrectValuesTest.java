package ru.qatools.properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.qatools.properties.exeptions.PropertyLoaderException;
import ru.qatools.properties.testdata.ProxyPropertiesFactory;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 4:38 PM
 */
public class IncorrectValuesTest {

    @Before
    public void setupSystemProperties() {
        System.setProperty("proxy.port", "aaa");
    }

    @Test(expected = PropertyLoaderException.class)
    public void checkIncorrectValueOccursTheException() {
        ProxyPropertiesFactory.createProxyPropertiesWithSystemOverride();
    }

    @After
    public void clearSystemProperties() {
        System.clearProperty("proxy.port");
    }

}
