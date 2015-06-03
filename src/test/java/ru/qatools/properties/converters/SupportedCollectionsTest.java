package ru.qatools.properties.converters;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
@RunWith(Parameterized.class)
public class SupportedCollectionsTest {

    private StringSplitter splitter = new RegexStringSplitter(",");

    private ConverterManager manager = new ConverterManager(splitter);

    @Parameterized.Parameter(0)
    public String origin;

    @Parameterized.Parameter(1)
    public String[] expect;

    @Parameterized.Parameter(2)
    public Class elementType;

    @Parameterized.Parameter(3)
    public Class collectionType;

    @Parameterized.Parameters
    public static Collection<Object[]> data() throws MalformedURLException {
        return Arrays.asList(new Object[][]{
                {"hello,world", new String[]{"hello", "world"}, String.class, List.class},
                {"hello,world", new String[]{"hello", "world"}, String.class, LinkedList.class},
                {"hello,world", new String[]{"hello", "world"}, String.class, ArrayList.class},
                {"hello,world", new String[]{"hello", "world"}, String.class, Collection.class},
                {"hello,world", new String[]{"hello", "world"}, String.class, HashSet.class},
                {"hello,world", new String[]{"hello", "world"}, String.class, Set.class}
        });
    }

    @Test
    public void shouldConvert() throws Exception {
        Object converted = manager.convert(collectionType, elementType, origin);

        assertThat(converted, instanceOf(Collection.class));

        Collection result = (Collection) converted;
        assertThat(result, (Matcher) Matchers.hasItems(expect));

        //noinspection unchecked
        assertTrue(String.format("Type of converted object <%s> doesn't assignable from <%s>",
                converted.getClass(), collectionType), collectionType.isAssignableFrom(converted.getClass()));
    }
}
