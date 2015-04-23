# QATools Properties

[![release](http://github-release-version.herokuapp.com/github/qatools/properties/release.svg?style=flat)](https://github.com/yandex-qatools/properties/releases/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.qatools.commons/properties/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/ru.qatools.commons/properties)  

This library provides convinient way to work with properties. Using power of [Apache Common Bean Utils](http://commons.apache.org/proper/commons-beanutils/) it can handle property-files on hard drive, in classpath or get values from system properties

## Maven Dependencies

Latest stable version:
```xml
<dependency>
    <groupId>ru.qatools.commons</groupId>
    <artifactId>properties</artifactId>
    <version>${LATEST_VERSION}</version>
</dependency>
```

# Getting Started

### Property class

```java
@Resource.Classpath("proxy.properties")
public class ProxyProperties {
 
    public ProxyProperties() {
        PropertyLoader.newInstance().populate(this);
    }
 
    @Property("proxy.host")
    private String host;
 
    @Property("proxy.port")
    private int port;
 
    @Property("proxy.active")
    private boolean active;
 
    public String getHost() {
        return host;
    }
 
    public int getPort() {
        return port;
    }
 
    public boolean isActive() {
        return active;
    }
}
```

### Property file

Put in your resources directory (mainly it `src/main/resources`) file `proxy.properties`

```properties
proxy.host=proxy.yandex.ru
proxy.port=3133
proxy.use=false
```

### Usage

Now you can get `proxy.host` property value with  

```java
String host = new ProxyProperties().getHost();
```

It's easy to override value from system properties. E.g. when you run your code with `-Dproxy.host=ya.ru` it overrides the default value in properties file.

The complete start guide can be found on [wiki](http://wiki.qatools.ru/display/COMMONS/Properties).
