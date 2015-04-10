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
