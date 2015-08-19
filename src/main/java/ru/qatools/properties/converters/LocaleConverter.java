package ru.qatools.properties.converters;

import java.util.Locale;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 19.08.15
 */
public class LocaleConverter implements Converter<Locale> {

    @Override
    public Locale convert(String from) throws Exception {
        return Locale.forLanguageTag(from);
    }
}
