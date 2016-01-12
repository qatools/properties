package ru.qatools.properties.converters;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 12.01.16
 */
public class PatternConverterTest {

    @Test
    public void shouldConvert() throws Exception {
        String regex = ".*";
        Pattern actual = new PatternConverter().convert(regex);
        Pattern expected = Pattern.compile(regex);
        assertThat(actual.flags(), is(expected.flags()));
        assertThat(actual.pattern(), is(expected.pattern()));
    }
}