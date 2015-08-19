package ru.qatools.properties.converters;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
        register(File.class, new FileConverter());
        register(Path.class, new PathConverter());
        register(Locale.class, new LocaleConverter());

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
     */
    public <T> void register(Class<T> type, Converter<T> converter) {
        addConverter(type, converter);
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
    protected <T> Converter<T> find(Class<T> type) throws ConversionException {
        if (type.isEnum()) {
            //noinspection unchecked
            return new EnumConverter((Class<? extends Enum>) type);
        }
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            Converter childConverter = find(componentType);
            //noinspection unchecked
            return new ArrayConverter(childConverter, componentType, stringSplitter);
        }
        //noinspection unchecked
        Converter<T> converter = (Converter<T>) storage.get(type);
        if (converter == null) {
            throw new ConversionException(String.format("Could not find converter for type <%s>", type));
        }
        return converter;
    }

    /**
     * Convert given string to type.
     *
     * @throws ConversionException If can't find converter for given type.
     * @see #find(Class)
     */
    public Object convert(Class<?> type, String origin) throws ConversionException {
        if (origin == null) {
            return convertNullValue(type);
        }

        Converter converter = find(type);
        try {
            return converter.convert(origin);
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert string <%s> to type <%s>", origin, type), e);
        }
    }

    /**
     * Convert null to given type.
     *
     * @throws ConversionException if any occurs.
     */
    protected Object convertNullValue(Class type) throws ConversionException {
        try {
            return type.isPrimitive() ? Array.get(Array.newInstance(type, 1), 0) : null;
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert null to primitive type <%s>", type), e);
        }
    }


    /**
     * Convert given string to specified collection with given element type.
     *
     * @throws ConversionException If any occurs.
     * @see #find(Class)
     */
    public <T> Object convert(Class collectionType, Class<T> elementType, String origin) throws ConversionException {
        Converter<T> elementConverter = find(elementType);
        CollectionConverter<T> converter = new CollectionConverter<>(elementConverter, stringSplitter);

        try {
            Collection<T> converted = converter.convert(origin);
            return castCollectionToType(collectionType, converted);
        } catch (Exception e) {
            throw new ConversionException(String.format("Could not convert string <%s> to collection <%s> " +
                    "with element type <%s>", origin, collectionType, elementType), e);
        }
    }

    /**
     * Create an instance of specified collection with given element type.
     */
    @SuppressWarnings("unchecked")
    protected <T> Collection<T> castCollectionToType(Class collectionType, Collection<T> converted) throws ConversionException {
        if (!Collection.class.isAssignableFrom(collectionType)) {
            throw new ConversionException("Collection type should extends collection" + collectionType);
        }

        if (collectionType.isInterface()) {
            if (collectionType.isAssignableFrom(Set.class)) {
                return Collections.unmodifiableSet(new HashSet<>(converted));
            }
            if (collectionType.isAssignableFrom(List.class)) {
                return Collections.unmodifiableList(new LinkedList<>(converted));
            }
            if (collectionType.isAssignableFrom(Collection.class)) {
                return Collections.unmodifiableCollection(converted);
            }
            throw new ConversionException("Unsupported collection type " + collectionType);
        }
        try {
            Constructor constructor = collectionType.getConstructor(Collection.class);
            return (Collection<T>) constructor.newInstance(converted);
        } catch (Exception e) {
            throw new ConversionException("Could not create an instance of " + collectionType, e);
        }
    }
}
