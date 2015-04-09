package ru.yandex.qatools.properties.exeptions;

/**
 * @author Artem Eroshenko eroshenkoam
 *         5/31/13, 7:03 PM
 */
public class PropertyLoaderException extends RuntimeException {

    public PropertyLoaderException(String message) {
        super(message);
    }

    public PropertyLoaderException(String message, Throwable e) {
        super(message, e);
    }
}
