package ru.qatools.properties.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * User: eroshenkoam
 * Date: 11/9/12, 5:25 PM
 */
public final class PropertiesUtils {

    public static final String VAR_PREFIX = "${";
    public static final String VAR_POSTFIX = "}";

    private PropertiesUtils() {
        throw new UnsupportedOperationException();
    }

    public static String injectProperties(String text, Properties properties) {
        if (text != null && text.contains(VAR_PREFIX)) {
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                text = text.replace(VAR_PREFIX + key + VAR_POSTFIX, value);
            }
        }
        return text;
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
