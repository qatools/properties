package ru.qatools.properties.converters;

import java.io.File;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class FileConverter implements Converter<File> {

    @Override
    public File convert(String from) throws Exception {
        return new File(from);
    }
}
