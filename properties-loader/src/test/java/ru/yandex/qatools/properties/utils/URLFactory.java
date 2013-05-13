package ru.yandex.qatools.properties.utils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/5/13, 5:16 PM
 */
public class URLFactory {

    public static URL createURLOrDie(String s) {
        try {
            return new URL(s);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
