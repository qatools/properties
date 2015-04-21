package ru.qatools.properties;

import org.junit.Test;
import ru.qatools.properties.testdata.SimpleProperties;
import ru.qatools.properties.exeptions.PropertyLoaderException;
import ru.qatools.properties.testdata.NullKeyFieldDecorator;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 10.04.15
 */
public class CustomFieldDecoratorTest {

    @Test(expected = PropertyLoaderException.class)
    public void shouldThrowAnExceptionIfNullKey() throws Exception {
        PropertyLoader.newInstance()
                .withFieldDecorator(new NullKeyFieldDecorator())
                .populate(SimpleProperties.class);
    }
}
