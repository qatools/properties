package ru.yandex.qatools.properties;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.properties.converters.CharsetConverter;
import ru.yandex.qatools.properties.converters.URIConverter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 10.04.15
 */
@RunWith(Parameterized.class)
public class ConvertersTest {

    @Parameterized.Parameter(0)
    public Converter converter;

    @Parameterized.Parameter(1)
    public String stringValue;

    @Parameterized.Parameter(2)
    public Object expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new CharsetConverter(), "UTF-8", StandardCharsets.UTF_8},
                {new URIConverter(), "file://a.txt", URI.create("file://a.txt")}
        });
    }

    @Test(expected = ConversionException.class)
    public void shouldThrowAnExceptionIfInvalidType() throws Exception {
        converter.convert(Object.class, stringValue);
    }

    @Test
    public void shouldConvertToDefaultType() throws Exception {
        Object converted = converter.convert(null, stringValue);
        assertThat(converted, is(expected));
    }
}
