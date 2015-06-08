package ru.qatools.properties.converters;

import java.lang.reflect.Array;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class ArrayConverter implements Converter {

    private Converter childConverter;

    private Class childType;

    private StringSplitter stringSplitter;

    public ArrayConverter(Converter childConverter, Class childType, StringSplitter stringSplitter) {
        this.childConverter = childConverter;
        this.childType = childType;
        this.stringSplitter = stringSplitter;
    }

    @Override
    public Object convert(String from) throws Exception {
        String[] children = stringSplitter.split(from);

        if (children.length == 1 && children[0].trim().isEmpty()) {
            return Array.newInstance(childType, 0);
        }

        Object result = Array.newInstance(childType, children.length);
        for (int i = 0; i < children.length; i++) {
            Array.set(result, i, childConverter.convert(children[i]));
        }
        return result;
    }
}
