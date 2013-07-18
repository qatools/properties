package ru.yandex.qatools.properties.annotations;

import ru.yandex.qatools.properties.providers.PropertyProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 17:28
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface With {
    Class<? extends PropertyProvider> value();
}
