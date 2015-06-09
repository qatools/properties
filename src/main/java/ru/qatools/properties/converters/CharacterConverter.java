package ru.qatools.properties.converters;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class CharacterConverter implements Converter<Character> {

    @Override
    public Character convert(String from) throws Exception {
        if (from.length() == 1) {
            return from.charAt(0);
        }
        throw new ConversionException("Converted string should contains only one character");
    }
}
