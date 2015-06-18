package ru.qatools.properties.converters;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class PathConverter implements Converter<Path> {

    @Override
    public Path convert(String from) throws Exception {
        return Paths.get(from);
    }
}
