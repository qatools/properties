package ru.qatools.properties.testdata;

import ru.qatools.properties.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 4:32 PM
 */

@Resource.File({ProxyPropertiesFactory.FILE_PATH, ProxyPropertiesFactory.FILE_LOGIN_PATH})
public class ProxyPropertiesWithFewFileAnnotation extends ProxyProperties {
}
