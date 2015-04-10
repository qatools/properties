package ru.yandex.qatools.properties;

import org.junit.Test;
import ru.yandex.qatools.properties.exeptions.PropertyLoaderException;
import ru.yandex.qatools.properties.testdata.NullKeyFieldDecorator;
import ru.yandex.qatools.properties.testdata.SimpleProperties;

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
