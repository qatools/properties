# QATools Properties

* [Maven Dependencies](#maven-dependencies)
* [Getting Started](#getting-started)
    * [Project Structure](#project-structure)
    * [Property File Creation](#property-file-creation)
    * [Property Class Creation](#property-class-creation)
    * [Property Class Initialisation](#property-class-initialisation)
    * [System Properties Overriding](#system-properties-overriding)
    * [Properties Priorities](#properties-priority)
* [Property Loader Extension](#property-loader-extension)
    * [Conversion Data Types](#conversion-data-types)
    * [Creating Specific Converter][creation-specific-converter]

В тестах часто приходится работать с переменными окружения. Причем в зависимости от этих переменных поведение тестов
может меняться. Для работы с properties-файлами мы разработали несложную обертку.
Технически QATools Properties является настройкой над
[Apache Common Bean Utils](http://commons.apache.org/proper/commons-beanutils/)

## Maven Dependencies

Стабильная версия:
```xml
<dependency>
    <groupId>ru.yandex.qatools.properties</groupId>
    <artifactId>properties-loader</artifactId>
    <version>1.3-SNAPSHOT</version>
</dependency>
```

## Getting Started

Работу библиотеки легче всего продемонстрировать на несложном примере конфигурирования прокси.

### Project Structure

В примере будет использоваться стандартная стурктура директорий, которую декларирует maven:

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

Для начала, в ресурсах создаем файл `proxy.properties` для конфигурации прокси:

```properties
proxy.host=proxy.yandex.ru
proxy.port=3133
proxy.use=false
```

### Property Class Creation

Далее, в исходниках создадим класс `ProxyProperties`, который имплементирует настройки прокси:

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

Если создавать проперти-файл вам не нужно, а вы хотите задать значения переменных по умолчанию в коде и при необходимости 
перегружать их значениями из системных проперти, то просто не аннотируйте класс:

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

### Property Class Initialisation

В конструкторе класса `ProxyProperties` вызывается статический метод `PropertyLoader.populate(this)`, 
который инициализирует поля класса из файла `proxy.properties` 
в соответствии со значением аннотаций `@Property`.

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
["Создание собственного конвертера"][creation-specific-converter]

[creation-specific-converter]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/creation-specific-converter.ru.md
