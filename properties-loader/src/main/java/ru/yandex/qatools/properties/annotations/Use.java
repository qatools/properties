package ru.yandex.qatools.properties.annotations;

import org.apache.commons.beanutils.Converter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/13/13, 7:26 PM
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Use {

    public Class<? extends Converter> value();
}
