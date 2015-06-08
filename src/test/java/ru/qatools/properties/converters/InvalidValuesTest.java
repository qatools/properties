package ru.qatools.properties.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qatools.properties.testdata.PropEnum;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
@RunWith(Parameterized.class)
public class InvalidValuesTest {

    private StringSplitter splitter = new RegexStringSplitter(",");

    private ConverterManager manager = new ConverterManager(splitter);

    @Parameterized.Parameter(0)
    public String origin;

    @Parameterized.Parameter(1)
    public Class type;

    @Parameterized.Parameters(name = "convert \"{0}\" to {1}")
    public static Collection<Object[]> data() throws MalformedURLException {
        return Arrays.asList(new Object[][]{
                {"string", Double.class},
                {"string", Double[].class},
                {"", Double.TYPE},
                {"string", double[].class},

                {"string", Float.class},
                {"string", Float[].class},
                {"", Float.TYPE},
                {", string", float[].class},

                {"string", Long.class},
                {"string", Long[].class},
                {"", Long.TYPE},
                {"string", long[].class},

                {"string", Integer.class},
                {"string", Integer[].class},
                {"", Integer.TYPE},
                {"string", int[].class},

                {"string", Short.class},
                {"string", Short[].class},
                {"", Short.TYPE},
                {"string", short[].class},

                {"string", Byte.class},
                {"string", Byte[].class},
                {"", Byte.TYPE},
                {"string", byte[].class},

                {"string", Character.class},
                {"string", Character[].class},
                {"", Character.TYPE},
                {"string", char[].class},

                {"string", Boolean.class},
                {"string", Boolean[].class},
                {"", Boolean.TYPE},
                {"string", boolean[].class},

                {"string", URL.class},
                {"string", URL[].class},

                {"\\", URI.class},
                {"\\", URI[].class},

                {"string", Charset.class},
                {"string", Charset[].class},

                {"string", PropEnum.class},
                {"string", PropEnum[].class},

                {"string", MyType.class}
        });
    }

    @Test(expected = ConversionException.class)
    public void shouldConvert() throws Exception {
        manager.convert(type, origin);
    }

    static class MyType {
    }
}
