package ru.yandex.qatools.properties.decorators;

import java.lang.reflect.Field;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Eroshenko Artem eroshenkoam@yandex-team.ru
 *         Date: 09.04.15
 */
public interface FieldDecorator {

    /**
     * Returns true if given field should be decorated,
     * false otherwise.
     */
    boolean shouldDecorate(Field field);

    /**
     * Get field id by given {@link Field}. This method calls <b>only</b>
     * if {@link #shouldDecorate(Field)} method for the same field returns true.
     *
     * @return field id.
     */
    String getFieldKey(Field field);
}
