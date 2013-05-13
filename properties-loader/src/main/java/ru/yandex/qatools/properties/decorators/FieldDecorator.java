package ru.yandex.qatools.properties.decorators;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * User: eroshenkoam
 * Date: 11/13/12, 1:17 PM
 */
public interface FieldDecorator {

    public Object decorate(Field field);
}
