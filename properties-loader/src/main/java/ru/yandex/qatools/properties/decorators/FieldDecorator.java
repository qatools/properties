package ru.yandex.qatools.properties.decorators;

import java.lang.reflect.Field;

/**
 * User: eroshenkoam
 * Date: 11/13/12, 1:17 PM
 */
public interface FieldDecorator {

    Object decorate(Field field);
}
