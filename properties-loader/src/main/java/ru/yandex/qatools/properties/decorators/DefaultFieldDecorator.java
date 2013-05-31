package ru.yandex.qatools.properties.decorators;

import org.apache.commons.beanutils.*;
import ru.yandex.qatools.properties.annotations.Property;
import ru.yandex.qatools.properties.annotations.Use;
import ru.yandex.qatools.properties.converters.URIConverter;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Properties;

/**
 * User: eroshenkoam
 * Date: 11/13/12, 1:26 PM
 */
public class DefaultFieldDecorator implements FieldDecorator {

    private final ConvertUtilsBean converters;

    private Properties properties;

    public DefaultFieldDecorator(Properties properties) {
        this.converters = BeanUtilsBean.getInstance().getConvertUtils();
        this.converters.register(true, false, -1);
        this.converters.register(new URIConverter(), URI.class);
        this.properties = properties;
    }

    @Override
    public Object decorate(Field field) {
        if (!field.isAnnotationPresent(Property.class)) {
            return null;
        }

        String key = field.getAnnotation(Property.class).value();

        if (key == null || "".equals(key)) {
            return null;
        }

        String value = properties.getProperty(key);

        if (value == null || "".equals(value)) {
            return null;
        }

        Converter converter = getConverterFrom(field);

        if (converter == null) {
            return null;
        }

        try {
            return converter.convert(field.getType(), properties.getProperty(key));
        } catch (ConversionException e) {
            return null;
        }
    }

    private Converter getConverterFrom(Field field) {
        if (field.isAnnotationPresent(Use.class)) {
            return createNewInstanceFromUseAnnotations(field);
        } else {
            return converters.lookup(field.getType());
        }
    }

    private Converter createNewInstanceFromUseAnnotations(Field field) {
        try {
            return field.getAnnotation(Use.class).value().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
