package ru.qatools.properties.converters;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class CollectionConverter<T> implements Converter<Object> {

    private Converter<T> childConverter;

    private StringSplitter stringSplitter;

    public CollectionConverter(Converter<T> childConverter, StringSplitter stringSplitter) {
        this.childConverter = childConverter;
        this.stringSplitter = stringSplitter;
    }

    @Override
    public Collection<T> convert(String from) throws Exception {
        String[] children = stringSplitter.split(from);
        Collection<T> result = new LinkedList<>();

        for (String child : children) {
            result.add(childConverter.convert(child));
        }
        return result;
    }
}
