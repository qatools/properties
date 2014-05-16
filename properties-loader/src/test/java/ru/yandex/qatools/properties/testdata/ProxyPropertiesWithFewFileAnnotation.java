package ru.yandex.qatools.properties.testdata;

import ru.yandex.qatools.properties.annotations.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 4:32 PM
 */

@Resource.File({ProxyPropertiesFactory.FILE_PATH, ProxyPropertiesFactory.FILE_LOGIN_PATH})
public class ProxyPropertiesWithFewFileAnnotation extends ProxyProperties {
}
