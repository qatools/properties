package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class ByteConverter implements Converter<Byte> {

    @Override
    public Byte convert(String from) throws Exception {
        return Byte.parseByte(from);
    }
}
