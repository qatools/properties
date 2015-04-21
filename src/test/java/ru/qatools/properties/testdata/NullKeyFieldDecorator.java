package ru.qatools.properties.testdata;

import ru.qatools.properties.decorators.FieldDecorator;

import java.lang.reflect.Field;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 10.04.15
 */
public class NullKeyFieldDecorator implements FieldDecorator {

    @Override
    public boolean shouldDecorate(Field field) {
        return true;
    }

    @Override
    public String getFieldKey(Field field) {
        return null;
    }
}
