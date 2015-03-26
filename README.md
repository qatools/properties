# QATools Properties

[![release](http://github-release-version.herokuapp.com/github/yandex-qatools/properties/release.svg?style=flat)](https://github.com/yandex-qatools/properties/releases/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.yandex.qatools.properties/properties-loader/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/ru.yandex.qatools.properties/properties-loader)  

This library provides convinient way to work with properties. Using power of [Apache Common Bean Utils](http://commons.apache.org/proper/commons-beanutils/) it can handle property-files on hard drive, in classpath or get values from system properties

## Maven Dependencies

Latest stable version:
```xml
<dependency>
    <groupId>ru.yandex.qatools.properties</groupId>
    <artifactId>properties-loader</artifactId>
    <version>1.5</version>
</dependency>
```

# Getting Started

### Property Class Creation

```java
@Resource.Classpath("proxy.properties") // default values in  resources/proxy.properties
public class ProxyProperties {

    public ProxyProperties() {
        PropertyLoader.populate(this); 
    }

    @Property("proxy.host") // key of property witch you want to handle
    private String host;

    public String getHost() {
        return host;
    }
}
```

### Property File Creation

Put in your resources directory (mainly it `src/main/resources`) file `proxy.properties`

```properties
proxy.host=proxy.yandex.ru
```

### Usage

Now you can get `proxy.host` property value with  

```java
String host = new ProxyProperties().getHost();
```

It's easy to override value from system properties. E.g. when you run your code with `-Dproxy.host=ya.ru` it overrides the default value in properties file.

More about [Properties Priorities](https://github.com/yandex-qatools/properties/wiki/Properties-Priorities), [Conversion-Data-Types](https://github.com/yandex-qatools/properties/wiki/Conversion-Data-Types) can be foud on wiki

