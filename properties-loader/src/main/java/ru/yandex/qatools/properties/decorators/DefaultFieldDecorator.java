package ru.yandex.qatools.properties.decorators;

import ru.yandex.qatools.properties.annotations.Property;

import java.lang.reflect.Field;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Eroshenko Artem eroshenkoam@yandex-team.ru
 *         Date: 09.04.15
 */
public class DefaultFieldDecorator implements FieldDecorator {

    @Override
    public boolean shouldDecorate(Field field) {
        return field.isAnnotationPresent(Property.class);
    }

    @Override
    public String getFieldKey(Field field) {
        return field.getAnnotation(Property.class).value();
    }
}
