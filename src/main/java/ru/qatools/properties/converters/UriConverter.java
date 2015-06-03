package ru.qatools.properties.converters;

import java.net.URI;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class UriConverter implements Converter<URI> {
    @Override
    public URI convert(String from) throws Exception {
        return URI.create(from.trim());
    }
}
