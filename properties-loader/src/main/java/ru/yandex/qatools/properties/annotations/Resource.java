package ru.yandex.qatools.properties.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User: eroshenkoam
 * Date: 11/9/12, 1:47 PM
 */
public interface Resource {

    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @Target({java.lang.annotation.ElementType.TYPE})
    public @interface File {
        String[] value();
    }

    @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    @Target({java.lang.annotation.ElementType.TYPE})
    public @interface Classpath {
        String[] value();
    }
}
