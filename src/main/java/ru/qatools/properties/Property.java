package ru.qatools.properties;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User: eroshenkoam
 * Date: 11/9/12, 1:48 PM
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD, ElementType.METHOD})
public @interface Property {

    String value();

}
