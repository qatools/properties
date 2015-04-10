package ru.qatools.properties.utils;

import java.io.*;
import java.nio.charset.Charset;
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
        if (inputStream == null) {
            return new Properties();
        } else {
            return readProperties(new InputStreamReader(inputStream, Charset.defaultCharset()));
        }
    }

    public static Properties readProperties(Reader reader) {
        Properties result = new Properties();
        try {
            result.load(reader);
            return result;
        } catch (IOException e) {
            return result;
        }
    }
}
