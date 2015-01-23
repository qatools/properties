# QATools Properties

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.yandex.qatools.properties/properties-loader/badge.svg?style=flat)](http://mvnrepository.com/artifact/ru.yandex.qatools.properties/properties-loader) [![build](https://img.shields.io/teamcity/http/teamcity.qatools.ru/s/properties_development_deploy.svg?style=flat)](http://teamcity.qatools.ru/viewType.html?buildTypeId=properties_development_deploy&guest=1)

* [Maven Dependencies](#maven-dependencies)
* [Getting Started](#getting-started)
    * [Project Structure](#project-structure)
    * [Property File Creation](#property-file-creation)
    * [Property Class Creation](#property-class-creation)
    * [Property Class Initialization](#property-class-initialization)
    * [System Properties Overriding](#system-properties-overriding)
    * [Properties Priorities](#properties-priorities)
    * [Create Multi File Configuration][create-multi-file-configuration]
    * [Own Property-Provider][creation-custom-property-provider]
* [Property Loader Extension](#property-loader-extension)
    * [Conversion Data Types](#conversion-data-types)
    * [Creating Specific Converter][creation-custom-converter]

Your test often use some environment variables. And test excecution can depends on this enviroment. QATools Properties wraps [Apache Common Bean Utils](http://commons.apache.org/proper/commons-beanutils/) and make work with environment more clear and easy.

## Maven Dependencies

First step you need to add the following dependecy to your **pom.xml**:

```xml
<dependency>
    <groupId>ru.yandex.qatools.properties</groupId>
    <artifactId>properties-loader</artifactId>
    <version>{latest-version}</version>
</dependency>
```

## Getting Started

To explain how the properties work let's look to the following example. This example shows how to configure *proxy server* for your tests.

### Project Structure

We will use directory structure which Maven declare:

```
- pom.xml
+ src
  + test
    + java
      - ProxyProperties.java
    + resources
      - proxy.properties
```

### Property File Creation

First step create `proxy.properties` in resource directory for proxy configuration:

```properties
proxy.host=proxy.yandex.ru
proxy.port=3133
proxy.use=false
```

### Property Class Creation

Then create class `ProxyProperties`, which implements the configuration:

```java
@Resource.Classpath("proxy.properties") //for class initialization we will use a file proxy.poerties
public class ProxyProperties {

    public ProxyProperties() {
        PropertyLoader.populate(this); //initialize fields
    }

    @Property("proxy.host") //key which will be used to find property in environment
    private String host;

    @Property("proxy.port") //key which will be used to find property in environment
    private int port;

    @Property("proxy.active") //key which will be used to find property in environment
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

If you don't need to create a property file you can specify a defaults in core. In this case you can override defaults using system properties. *Note:* you don't need to annotate `ProxyProperties` using `@Resource` annotation. 
```java
public class ProxyProperties {

    public ProxyProperties() {
        PropertyLoader.populate(this);
    }

    @Property("proxy.host")
    private String host = "localhost"; //default value <localhost>

   ...
   
}
```

### Property Class Initialization

In `ProxyProperties` constructor call `PropertyLoader.populate(this)` method, it's initialize class fields in accordance with `@Property` annotation.

```java
ProxyProperties proxyProperties = new ProxyProperties();
assumeThat(proxyProperties.isActive(), equalTo(true));
assertThat(proxyProperties.getHost(), equatlTo("proxy.yandex.ru"));
assertThat(proxyProperties.getHost(), equatlTo(3133));
```

Usability of Properties is automating type casting of enviroment variables. If autocasting is impossible then variable was skipped. 

In our case `porxy.active = false`, it's mean by default proxy is disabled. You can override this variable using system property.

### System Properties Overriding

Configuration in property file is default and always can be overrided by system properties. 

### Properties Priorities

There are the following property initialization order:
- system properties. High priority
- values from property file. Medium priority
- default values from code. Low priority

You can change this order using an example [download configurations depending on environment][create-multi-file-configuration] or create [your own property loader][creation-custom-property-provider].

Already available  property loader which convert paths like this:
```java
@Resource.Classpath("${system.file.name}.path.${map.scope.value}.properties")
```

to real pathes. You can enable it using annotation `@With`:

```java
@With(MapOrSyspropPathReplacerProvider.class)
```

More information [here][custom-provider-test]

## Property Loader Extension

### Conversion Data Types

For know we support auto type casting for all primary types and few more:

```java
@Property("supported.double")
private double aDouble;

@Property("supported.float")
private float aFloat;

@Property("supported.short")
private short aShort;

@Property("supported.int")
private int aInt;

@Property("supported.byte")
private byte aByte;

@Property("supported.string")
private String aString;

@Property("supported.boolean")
private boolean aBoolean;

@Property("supported.long")
private long aLong;

@Property("supported.char")
private char aChar;

@Property("supported.url")
private URL aURL;

@Property("supported.uri")
private URI aURI;

```

Also you can define your own converter. How to create your own converter you can read in [this note][creation-custom-converter]

## Ready to use converters: 
- `ru.yandex.qatools.properties.converters.EnumConverter`

[custom-provider-test]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/test/java/ru/yandex/qatools/properties/CustomPropertyProviderTest.java
[creation-custom-converter]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/creation-custom-converter.ru.md
[create-multi-file-configuration]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/create-multi-file-configuration.ru.md
[creation-custom-property-provider]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/creation-custom-property-provider.ru.md
