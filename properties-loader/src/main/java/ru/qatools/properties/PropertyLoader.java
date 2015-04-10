package ru.qatools.properties;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import ru.qatools.properties.converters.CharsetConverter;
import ru.qatools.properties.converters.URIConverter;
import ru.qatools.properties.decorators.DefaultFieldDecorator;
import ru.qatools.properties.decorators.FieldDecorator;
import ru.qatools.properties.providers.PropertyProvider;
import ru.qatools.properties.exeptions.PropertyLoaderException;
import ru.qatools.properties.providers.DefaultPropertyProvider;

import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Eroshenko Artem eroshenkoam@yandex-team.ru
 *         Date: 09.04.15
 */
public class PropertyLoader {

    protected ClassLoader classLoader = getClass().getClassLoader();

    protected Properties defaults = new Properties();

    protected Properties compiled = new Properties();

    protected FieldDecorator fieldDecorator = new DefaultFieldDecorator();

    protected PropertyProvider propertyProvider = new DefaultPropertyProvider();

    protected Map<String, String> cache = new HashMap<>();

    protected final ConvertUtilsBean converters = new ExtendedConvertUtilsBean();

    PropertyLoader() {
    }

    public void populate(Object bean) {
        compiled.putAll(defaults);
        compiled.putAll(propertyProvider.provide(classLoader, bean));

        initFields(bean);
    }

    protected void initFields(Object bean) {
        Class<?> clazz = bean.getClass();
        while (clazz != Object.class) {
            initFields(bean, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
    }

    protected void initFields(Object bean, Field[] fields) {
        for (Field field : fields) {
            if (fieldDecorator.shouldDecorate(field)) {
                String key = getFieldKey(field);
                String stringValue = getFieldValue(key);
                if (stringValue == null) {
                    continue;
                }
                Object value = convertValueForField(field, stringValue);
                setValueToField(field, bean, value);
            }
        }
    }

    protected String getFieldKey(Field field) {
        String key = fieldDecorator.getFieldKey(field);

        if (key == null) {
            throw new PropertyLoaderException(String.format(
                    "Field decorator <%s> returned null key for field <%s> ", fieldDecorator, field.getName()
            ));
        }
        return key;
    }

    protected String getFieldValue(String key) {
        if (!cache.containsKey(key)) {
            String value = compiled.getProperty(key);
            cache.put(key, value);
        }

        return cache.get(key);
    }

    protected Object convertValueForField(Field field, String stringValue) {
        Class<?> type = field.getType();
        try {
            return converters.convert(stringValue, type);
        } catch (Exception e) {
            throw new PropertyLoaderException(String.format(
                    "Can't convert value <%s> to type <%s> for field <%s>", stringValue, type, field.getName()
            ), e);
        }
    }

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

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public PropertyLoader withClassLoader(ClassLoader classLoader) {
        setClassLoader(classLoader);
        return this;
    }

    public Properties getDefaults() {
        return defaults;
    }

    public void setDefaults(Properties defaults) {
        this.defaults = defaults;
    }

    public PropertyLoader withDefaults(Properties defaults) {
        setDefaults(defaults);
        return this;
    }

    public FieldDecorator getFieldDecorator() {
        return fieldDecorator;
    }

    public void setFieldDecorator(FieldDecorator fieldDecorator) {
        this.fieldDecorator = fieldDecorator;
    }

    public PropertyLoader withFieldDecorator(FieldDecorator fieldDecorator) {
        setFieldDecorator(fieldDecorator);
        return this;
    }

    public PropertyProvider getPropertyProvider() {
        return propertyProvider;
    }

    public void setPropertyProvider(PropertyProvider propertyProvider) {
        this.propertyProvider = propertyProvider;
    }

    public PropertyLoader withPropertyProvider(PropertyProvider propertyProvider) {
        setPropertyProvider(propertyProvider);
        return this;
    }

    public PropertyLoader register(Converter converter, Class<?> type) {
        converters.register(converter, type);
        return this;
    }

    public static PropertyLoader newInstance() {
        return new PropertyLoader();
    }

    protected static class ExtendedConvertUtilsBean extends ConvertUtilsBean {

        public ExtendedConvertUtilsBean() {
            register(true, true, 0);
            register(new CharsetConverter(), Charset.class);
            register(new URIConverter(), URI.class);
        }

        @Override
        public Object convert(String value, Class clazz) {
            if (clazz.isEnum()) {
                return Enum.valueOf(clazz, value.toUpperCase().trim());
            } else {
                return super.convert(value, clazz);
            }
        }
    }
}
