package ru.qatools.properties.testdata;

import ru.qatools.properties.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         4/17/13, 2:29 PM
 */
@Resource.File("absence.file")
@Resource.Classpath("absence.resource")
public class PropertiesWithAbsenceFile {

    private String property = "default value";

    public String getProperty() {
        return property;
    }
}
