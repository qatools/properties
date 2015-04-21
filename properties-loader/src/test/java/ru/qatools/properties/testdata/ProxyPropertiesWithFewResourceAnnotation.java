package ru.qatools.properties.testdata;

import ru.qatools.properties.annotations.Resource;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/6/13, 4:32 PM
 */

@Resource.Classpath({ProxyPropertiesFactory.RESOURCE_PATH, ProxyPropertiesFactory.RESOURCE_LOGIN_PATH})
public class ProxyPropertiesWithFewResourceAnnotation extends ProxyProperties {
}
