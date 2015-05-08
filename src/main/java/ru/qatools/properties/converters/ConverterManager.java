package ru.qatools.properties.converters;

import ru.qatools.properties.exeptions.ConversionException;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This class can help you convert some values from string representation.
 *
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 08.05.15
 */
public class ConverterManager {

    public static final String COMMA = ",";

    private final Map<Class, Converter> storage = new HashMap<>();

    private StringSplitter stringSplitter;

    public ConverterManager() {
        this(new RegexStringSplitter(COMMA));
    }

    /**
     * Creates an instance manager with given string splitter.
     */
    public ConverterManager(StringSplitter stringSplitter) {
        this.stringSplitter = stringSplitter;
        registerDefaults();
    }

    /**
     * Register converters supported by default.
     */
    protected void registerDefaults() {
        register(Long.class, new LongConverter());
        register(Integer.class, new IntegerConverter());
        register(Short.class, new ShortConverter());
        register(Byte.class, new ByteConverter());
        register(Double.class, new DoubleConverter());
        register(Float.class, new FloatConverter());
        register(Character.class, new CharacterConverter());
        register(Boolean.class, new BooleanConverter());

        register(String.class, new StringConverter());
        register(URL.class, new UrlConverter());
        register(URI.class, new UriConverter());
        register(Charset.class, new CharsetConverter());

        register(Long.TYPE, new LongConverter());
        register(Integer.TYPE, new IntegerConverter());
        register(Short.TYPE, new ShortConverter());
        register(Byte.TYPE, new ByteConverter());
        register(Character.TYPE, new CharacterConverter());
        register(Double.TYPE, new DoubleConverter());
        register(Float.TYPE, new FloatConverter());
        register(Boolean.TYPE, new BooleanConverter());
    }

    /**
     * Register converter.
     *
     * @see #addArrayConverter(Class, Converter)
     */
    public <T> void register(Class<T> type, Converter<T> converter) {
        addConverter(type, converter);
        addArrayConverter(type, converter);
    }

    /**
     * Register array converter for given type.
     *
     * @see #register(Class, Converter)
     */
    @SuppressWarnings("unchecked")
    protected <T> void addArrayConverter(Class<T> type, Converter<T> childConverter) {
        Class arrayType = Array.newInstance(type, 0).getClass();
        addConverter(arrayType, new ArrayConverter<>(childConverter, type, stringSplitter));
    }

    /**
     * Add converter to the storage.
     */
    protected <T> void addConverter(Class<T> type, Converter<T> converter) {
        storage.put(type, converter);
    }

    /**
     * Find converter for given type. Returns null if converter doesn't exists.
     */
    public <T> Converter<T> find(Class<T> type) {
        //noinspection unchecked
        return (Converter<T>) storage.get(type);
    }

    /**
     * Convert given string to type.
     *
     * @throws ConversionException If can't find converter for given type.
     * @see #find(Class)
     */
    public Object convert(Class type, String origin) throws ConversionException {
        if (type.isEnum()) {
            return Enum.valueOf(type, origin.toUpperCase().trim());
        }

        Converter converter = find(type);
        if (converter == null) {
            throw new ConversionException(String.format("Could not find converter for type <%s>", type));
        }

        try {
            return converter.convert(origin);
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert string <%s> to type <%s>", origin, type), e);
        }
    }
}
