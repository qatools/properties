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
@Resource.Classpath("proxy.properties") //для иницализации класса будет использоваться файл proxy.poerties
public class ProxyProperties {

    public ProxyProperties() {
        PropertyLoader.populate(this); //инициализация полей класса значениями из файла
    }

    @Property("proxy.host") //ключ проперти, по которой переменной будет выставлено значение
    private String host;

    @Property("proxy.port") //ключ проперти, по которой переменной будет выставлено значение
    private int port;

    @Property("proxy.active") //ключ проперти, по которой переменной будет выставлено значение
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
    private String host = "localhost"; //определено дефолтное значение <localhost>

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

Удобство заключается в том, что при инициализаци полей происходит автоматическое приведение типов. 
Если приведение типов невозможно, то переменная не инициализируется. 
В данном случае, проперти `porxy.active = false`, а значит по дефолту прокси использоваться не будет. 
Для того, чтобы воздействовать на этот механизм извне мы сделали возможность переопределения 
конфигурации значениям системных переменных. 

### System Properties Overriding

Конфигурации в файле является дефолтной, ее всегда можно переопределить через системные переменные. 
Так например, если запустить тот же самый код при выставленной системной проперти `proxy.active=true`, 
то ситемные значения перекрою значения из файла и мы пойдем по ветке иницализации прокси. 
Это становится удобно, когда вам необходимо запустить тесты на разных конфигурациях. 

### Properties Priorities

Таким образом при инициализации класса используется следующий порядок инициализации:
- значения системных переменных. Самый высокий приоритет.
- значения переменных из property-файла. Средний приоритет.
- объявленные значения переменных. Наименьший приоритет.

Если по какой-то причине вам не нравится существующий приоритет или принцип инициалиции объектов, 
то вы можете переопределить его следуя примеру [Загрузка кофигураций в зависимости от окружения (частный метод)][create-multi-file-configuration]
или воспользовавшись мануалом по созданию [Cобственного загрузчика пропертей][creation-custom-property-provider].

Уже доступен провайдер, позволяющий превращать пути вида
```java
@Resource.Classpath("${system.file.name}.path.${map.scope.value}.properties")
```
в реальные пути. Подключить его можно аннотацией

```java
@With(MapOrSyspropPathReplacerProvider.class)
```
Подробнее по применению в [тесте][custom-provider-test]


## Property Loader Extension

### Conversion Data Types

На данный момент поддерживается конвернтация во все примитивные типы данных + несколько стандартных:

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

Если вам по какой-то причине не хватает этих типов, то вы всегда можете сделать и использовать свой конвертер.
О том, как это можно использовать нужно почтитать в статье
["Создание собственного конвертера"][creation-custom-converter]

## Ready to use converters: 
- `ru.yandex.qatools.properties.converters.EnumConverter`


[custom-provider-test]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/test/java/ru/yandex/qatools/properties/CustomPropertyProviderTest.java
[creation-custom-converter]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/creation-custom-converter.ru.md
[create-multi-file-configuration]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/create-multi-file-configuration.ru.md
[creation-custom-property-provider]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/creation-custom-property-provider.ru.md
