package ru.qatools.properties.converters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.qatools.properties.exeptions.ConversionException;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
@RunWith(Parameterized.class)
public class UnsupportedCollectionsTest {

    private StringSplitter splitter = new RegexStringSplitter(",");

    private ConverterManager manager = new ConverterManager(splitter);

    @Parameterized.Parameter(0)
    public String origin;

    @Parameterized.Parameter(1)
    public Class elementType;

    @Parameterized.Parameter(2)
    public Class collectionType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws MalformedURLException {
        return Arrays.asList(new Object[][]{
                {"hello,world", String.class, HashMap.class},
                {"hello,world", String.class, Map.class},
                {"hello,world", String.class, Iterable.class},
                {"hello,world", String.class, MyList.class}
        });
    }

    @Test(expected = ConversionException.class)
    public void shouldNotConvert() throws Exception {
        manager.convert(collectionType, elementType, origin);
    }

    public interface MyList<T> extends List<T> {
    }
}
