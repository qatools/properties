package ru.qatools.properties.converters;

import java.nio.charset.Charset;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class CharsetConverter implements Converter<Charset> {

    @Override
    public Charset convert(String from) throws Exception {
        return Charset.forName(from.trim());
    }
}
