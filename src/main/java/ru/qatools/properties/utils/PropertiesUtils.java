package ru.qatools.properties.utils;

import java.io.*;
import java.util.Properties;

/**
 * User: eroshenkoam
 * Date: 11/9/12, 5:25 PM
 */
public final class PropertiesUtils {

    private PropertiesUtils() {
    }

    public static Properties readProperties(File file) {
        try {
            return readProperties(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return new Properties();
        }
    }

    /**
     * This is terminal method for {@code inputStream}.<br>
     * After properties loading it closes {@code inputStream}
     * with {@link InputStream#close()} method.
     *
     * @param inputStream stream, containing pairs of key=value properties
     * @return properties set inside {@code Properties}
     * @see Properties#load(InputStream)
     * @see InputStream#close()
     */
    public static Properties readProperties(InputStream inputStream) {
        Properties result = new Properties();
        if (inputStream != null) {
            try (InputStream is = inputStream) {
                result.load(is);
            } catch (IOException ignored) {
            }
        }
        return result;
    }
}
