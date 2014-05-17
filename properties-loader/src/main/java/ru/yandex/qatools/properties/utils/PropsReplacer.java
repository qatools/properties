package ru.yandex.qatools.properties.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmitry Baev charlie@yandex-team.ru
 *         Date: 16.05.14
 *
 *         Replace properties in each paths
 */
public class PropsReplacer {
    private List<String> paths;

    public PropsReplacer(String[] paths) {
        this.paths = Arrays.asList(paths);
    }

    /**
     * Fluent-api method. Replace properties in each path from paths field using pattern. Change paths filed value
     * @param pattern - pattern to replace. First group should contains property name
     * @param properties - list of properties using to replace
     * @return PropsReplacer
     */
    public PropsReplacer replaceProps(String pattern, Properties properties) {
        List<String> replaced = new ArrayList<>();
        for (String path : paths) {
            replaced.add(replaceProps(pattern, path, properties));
        }
        setPaths(replaced);
        return this;
    }

    /**
     * Replace properties in given path using pattern
     * @param pattern - pattern to replace. First group should contains property name
     * @param path - given path to replace in
     * @param properties - list of properties using to replace
     * @return path with replaced properties
     */
    private String replaceProps(String pattern, String path, Properties properties) {
        Matcher matcher = Pattern.compile(pattern).matcher(path);
        String replaced = path;
        while (matcher.find()) {
            replaced = replaced.replace(matcher.group(0), properties.getProperty(matcher.group(1), ""));
        }
        return replaced;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    /**
     * @return paths as Array of java.langString
     */
    public String[] getPathsAsArray() {
        return paths.toArray(new String[paths.size()]);
    }
}