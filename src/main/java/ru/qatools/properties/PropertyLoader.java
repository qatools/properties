package ru.qatools.properties;

import ru.qatools.properties.converters.ConversionException;
import ru.qatools.properties.converters.Converter;
import ru.qatools.properties.converters.ConverterManager;
import ru.qatools.properties.internal.PropertiesProxy;
import ru.qatools.properties.internal.PropertyInfo;
import ru.qatools.properties.providers.DefaultPropertyProvider;
import ru.qatools.properties.providers.PropertyProvider;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Eroshenko Artem eroshenkoam@yandex-team.ru
 *         Date: 09.04.15
 */
public class PropertyLoader {

    protected ClassLoader classLoader = getClass().getClassLoader();

    protected Properties defaults = new Properties();

    protected Properties compiled = new Properties();

    protected PropertyProvider propertyProvider = new DefaultPropertyProvider();

    protected final ConverterManager manager = new ConverterManager();

    /**
     * Do not instance this class by yourself. Use {@link #newInstance()} instead.
     */
    PropertyLoader() {
    }

    /**
     * Populate given bean using properties from {@link #defaults} and
     * {@link PropertyProvider#provide(ClassLoader, Class)}
     */
    public void populate(Object bean) {
        Objects.requireNonNull(bean);
        compileProperties(bean.getClass());

        Class<?> clazz = bean.getClass();

        while (clazz != Object.class) {
            Map<Field, PropertyInfo> propertyInfoMap = resolve(clazz.getDeclaredFields());

            for (Field field : propertyInfoMap.keySet()) {
                PropertyInfo info = propertyInfoMap.get(field);
                setValueToField(field, bean, info.getValue());
            }
            clazz = clazz.getSuperclass();
        }
    }

    public <T> T populate(Class<T> clazz) {
        Objects.requireNonNull(clazz);
        compileProperties(clazz);

        Set<Class> resolvedConfigs = new HashSet<>();
        resolvedConfigs.add(clazz);
        return populate(clazz, resolvedConfigs);
    }

    /**
     * Shortcut for {@link #populate(String, Class, Set)}.
     */
    public <T> T populate(Class<T> clazz, Set<Class> resolvedConfigs) {
        return populate(null, clazz, resolvedConfigs);
    }

    /**
     * Creates a proxy instance of given configuration.
     */
    public <T> T populate(String prefix, Class<T> clazz, Set<Class> resolvedConfigs) {
        checkConfigurationClass(clazz);
        Map<Method, PropertyInfo> properties = resolve(prefix, clazz.getMethods(), resolvedConfigs);
        //noinspection unchecked
        return (T) Proxy.newProxyInstance(classLoader, new Class[]{clazz},
                new PropertiesProxy(properties));
    }

    /**
     * Set given value to specified field of given object.
     *
     * @throws PropertyLoaderException if some exceptions occurs during reflection calls.
     * @see Field#setAccessible(boolean)
     * @see Field#set(Object, Object)
     */
    protected void setValueToField(Field field, Object bean, Object value) {
        try {
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            throw new PropertyLoaderException(
                    String.format("Can not set bean <%s> field <%s> value", bean, field), e
            );
        }
    }

    /**
     * Check that given class is interface.
     */
    protected void checkConfigurationClass(Class<?> clazz) {
        if (!clazz.isInterface()) {
            throw new PropertyLoaderException(clazz + " is not an interface");
        }
    }

    /**
     * Compile properties to {@link #compiled} field.
     */
    protected void compileProperties(Class<?> clazz) {
        compiled.putAll(defaults);
        compiled.putAll(propertyProvider.provide(classLoader, clazz));
    }

    /**
     * Shortcut for {@link #resolve(String, AnnotatedElement[], Set)}.
     */
    protected <T extends AnnotatedElement> Map<T, PropertyInfo> resolve(T[] elements) {
        return resolve(null, elements, Collections.<Class>emptySet());
    }

    /**
     * Return {@link PropertyInfo} for each of given elements.
     */
    protected <T extends AnnotatedElement> Map<T, PropertyInfo> resolve(String keyPrefix, T[] elements, Set<Class> resolvedConfigs) {
        Map<T, PropertyInfo> result = new HashMap<>();
        for (T element : elements) {
            try {
                result.putAll(resolveProperty(keyPrefix, element));
                result.putAll(resolveConfig(keyPrefix, element, resolvedConfigs));
            } catch (PropertyLoaderException e) {
                throw new PropertyLoaderException(String.format("Error while process %s", element), e);
            }
        }
        return result;
    }

    /**
     * Resolve the property for given element.
     */
    private <T extends AnnotatedElement> Map<T, PropertyInfo> resolveProperty(String keyPrefix, T element) {
        Map<T, PropertyInfo> result = new HashMap<>();
        if (!shouldDecorate(element)) {
            return result;
        }

        String key = getKey(keyPrefix, element);
        String defaultValue = getPropertyDefaultValue(element);
        String stringValue = compiled.getProperty(key, defaultValue);

        if (stringValue == null) {
            checkRequired(key, element);
            return result;
        }

        Object value = convertValue(element, stringValue);
        result.put(element, new PropertyInfo(key, stringValue, value));
        return result;
    }

    /**
     * Resolve the config for given element.
     */
    private <T extends AnnotatedElement> Map<T, PropertyInfo> resolveConfig(String keyPrefix, T element,
                                                                            Set<Class> resolvedConfigs) {
        Map<T, PropertyInfo> result = new HashMap<>();
        if (!element.isAnnotationPresent(Config.class)) {
            return result;
        }
        String prefix = concat(keyPrefix, element.getAnnotation(Config.class).prefix());

        Class<?> returnType = getValueType(element);
        checkRecursiveConfigs(resolvedConfigs, returnType);
        resolvedConfigs.add(returnType);

        Object proxy = populate(prefix, returnType, resolvedConfigs);
        result.put(element, new PropertyInfo(proxy));
        return result;
    }

    /**
     * Returns true if given annotatedElement should be decorated,
     * false otherwise.
     */
    protected boolean shouldDecorate(AnnotatedElement element) {
        return element.isAnnotationPresent(Property.class);
    }

    /**
     * Throws an exception if already meet the config.
     */
    private void checkRecursiveConfigs(Set<Class> resolvedConfigs, Class<?> configClass) {
        if (resolvedConfigs.contains(configClass)) {
            throw new PropertyLoaderException(String.format("Recursive configuration <%s>", configClass));
        }
    }

    /**
     * Throws an exception if given element is required.
     *
     * @see #isRequired(AnnotatedElement)
     */
    protected void checkRequired(String key, AnnotatedElement element) {
        if (isRequired(element)) {
            throw new PropertyLoaderException(String.format("Required property <%s> doesn't exists", key));
        }
    }

    /**
     * Returns true if annotatedElement marked as required with {@link Required}.
     */
    protected boolean isRequired(AnnotatedElement element) {
        return element.isAnnotationPresent(Required.class);
    }

    /**
     * Get the default value for given element. Annotation {@link Property} should
     * be present.
     */
    protected String getPropertyDefaultValue(AnnotatedElement element) {
        if (element.isAnnotationPresent(DefaultValue.class)) {
            return element.getAnnotation(DefaultValue.class).value();
        }
        return null;
    }

    /**
     * Get property key for specified element with given prefix. Annotation {@link Property} should
     * be present.
     */
    protected String getKey(String prefix, AnnotatedElement element) {
        String value = element.getAnnotation(Property.class).value();
        return concat(prefix, value);
    }

    /**
     * Concat the given prefixes into one.
     */
    protected String concat(String first, String second) {
        return first == null ? second : String.format("%s.%s", first, second);
    }

    /**
     * Convert given value to specified type. If given element annotated with {@link Use} annotation
     * use {@link #getConverterForElementWithUseAnnotation(AnnotatedElement)} converter, otherwise
     * if element has collection type convert collection and finally try to convert element
     * using registered converters.
     */
    protected Object convertValue(AnnotatedElement element, String value) {
        Class<?> type = getValueType(element);
        Type genericType = getValueGenericType(element);

        try {
            if (element.isAnnotationPresent(Use.class)) {
                Converter converter = getConverterForElementWithUseAnnotation(element);
                return converter.convert(value);
            }
            if (Collection.class.isAssignableFrom(type)) {
                return manager.convert(type, getCollectionElementType(genericType), value);
            }

            return manager.convert(type, value);
        } catch (Exception e) {
            throw new PropertyLoaderException(String.format(
                    "Can't convert value <%s> to type <%s>", value, type), e);
        }
    }

    /**
     * Returns the type of the value for given element. {@link Field} and {@link Method}
     * are only supported.
     */
    protected Class<?> getValueType(AnnotatedElement element) {
        if (element instanceof Field) {
            return ((Field) element).getType();
        }
        if (element instanceof Method) {
            return ((Method) element).getReturnType();
        }
        throw new PropertyLoaderException("Could not get element type");
    }

    /**
     * Returns the generic type of the value for given element. {@link Field} and {@link Method}
     * are only supported.
     */
    protected Type getValueGenericType(AnnotatedElement element) {
        if (element instanceof Field) {
            return ((Field) element).getGenericType();
        }
        if (element instanceof Method) {
            return ((Method) element).getGenericReturnType();
        }
        throw new PropertyLoaderException("Could not get generic type for element");
    }

    /**
     * Get collection element type for given type. Given type type should
     * be assignable from {@link Collection}. For collections without
     * generic returns {@link String}.
     */
    protected Class<?> getCollectionElementType(Type genericType) throws ConversionException {
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length != 1) {
                throw new ConversionException("Types with more then one generic are not supported");
            }

            Type type = typeArguments[0];
            if (type instanceof Class) {
                return (Class<?>) type;
            }

            throw new ConversionException(String.format("Could not resolve generic type <%s>", type));
        }
        return String.class;
    }

    /**
     * Returns new instance of converter specified in {@link Use} annotation for
     * given element.
     *
     * @param element given element with {@link Use} annotation.
     */
    protected Converter getConverterForElementWithUseAnnotation(AnnotatedElement element) {
        Class<? extends Converter> clazz = element.getAnnotation(Use.class).value();
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new PropertyLoaderException(String.format(
                    "Can't instance converter <%s>", clazz), e);
        }
    }

    /**
     * Register custom converter for given type.
     */
    public <T> PropertyLoader register(Converter<T> converter, Class<T> type) {
        manager.register(type, converter);
        return this;
    }

    public Properties getCompiled() {
        return compiled;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * @see #classLoader
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * Fluent-api builder.
     *
     * @see #setClassLoader(ClassLoader)
     */
    public PropertyLoader withClassLoader(ClassLoader classLoader) {
        setClassLoader(classLoader);
        return this;
    }

    public Properties getDefaults() {
        return defaults;
    }

    /**
     * @see #defaults
     */
    public void setDefaults(Properties defaults) {
        this.defaults = defaults;
    }

    /**
     * Fluent-api builder.
     *
     * @see #setDefaults(Properties)
     */
    public PropertyLoader withDefaults(Properties defaults) {
        setDefaults(defaults);
        return this;
    }

    public PropertyProvider getPropertyProvider() {
        return propertyProvider;
    }

    /**
     * @see #propertyProvider
     */
    public void setPropertyProvider(PropertyProvider propertyProvider) {
        this.propertyProvider = propertyProvider;
    }

    /**
     * Fluent-api builder.
     *
     * @see #setPropertyProvider(PropertyProvider)
     */
    public PropertyLoader withPropertyProvider(PropertyProvider propertyProvider) {
        setPropertyProvider(propertyProvider);
        return this;
    }

    /**
     * Do not instance class by yourself. Use this builder
     */
    public static PropertyLoader newInstance() {
        return new PropertyLoader();
    }
}
