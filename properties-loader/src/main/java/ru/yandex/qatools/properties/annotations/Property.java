package ru.yandex.qatools.properties.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User: eroshenkoam
 * Date: 11/9/12, 1:48 PM
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Property {

    String value();
}
