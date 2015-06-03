package ru.qatools.properties.converters;

import java.lang.reflect.Array;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class ArrayConverter<T> implements Converter<Object> {

    private Converter<T> childConverter;

    private Class<T> childType;

    private StringSplitter stringSplitter;

    public ArrayConverter(Converter<T> childConverter, Class<T> childType, StringSplitter stringSplitter) {
        this.childConverter = childConverter;
        this.childType = childType;
        this.stringSplitter = stringSplitter;
    }

    @Override
    public Object convert(String from) throws Exception {
        String[] children = stringSplitter.split(from);
        Object result = Array.newInstance(childType, children.length);

        for (int i = 0; i < children.length; i++) {
            Array.set(result, i, childConverter.convert(children[i]));
        }
        return result;
    }
}
