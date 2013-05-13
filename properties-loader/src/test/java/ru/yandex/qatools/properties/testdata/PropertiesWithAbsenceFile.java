package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.annotations.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         4/17/13, 2:29 PM
 */
@Resource.File("absence.file")
@Resource.Classpath("absence.resource")
public class PropertiesWithAbsenceFile {

    private String property;


    public String getProperty() {
        return property;
    }
}
