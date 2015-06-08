package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.06.15
 */
public class EnumConverter implements Converter {

    private Class<? extends Enum> type;

    public EnumConverter(Class<? extends Enum> type) {
        this.type = type;
    }

    @Override
    public Object convert(String from) throws Exception {
        return Enum.valueOf(type, from.toUpperCase().trim());
    }
}
