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

    public static Properties readProperties(InputStream inputStream) {
        Properties result = new Properties();
        if (inputStream != null) {
            try {
                result.load(inputStream);
            } catch (IOException ignored) {
            }
        }
        return result;
    }
}
