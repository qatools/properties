package ru.qatools.properties.converters;

import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 12.01.16
 */
public class PatternConverter implements Converter<Pattern> {

    @Override
    public Pattern convert(String from) throws Exception {
        return Pattern.compile(from);
    }
}
