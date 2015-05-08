package ru.qatools.properties.converters;

import java.net.URL;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class UrlConverter implements Converter<URL> {

    @Override
    public URL convert(String from) throws Exception {
        return new URL(from);
    }
}
