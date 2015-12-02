# QATools Properties

[![release](http://github-release-version.herokuapp.com/github/qatools/properties/release.svg?style=flat)](https://github.com/yandex-qatools/properties/releases/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.qatools.commons/properties/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/ru.qatools.commons/properties) [![build](https://img.shields.io/jenkins/s/http/ci.qatools.ru/properties_master-deploy.svg?style=flat)](http://ci.qatools.ru/job/properties_master-deploy/lastBuild/) [![covarage](https://img.shields.io/sonar/http/sonar.qatools.ru/ru.qatools.commons:properties/coverage.svg?style=flat)](http://sonar.qatools.ru/dashboard/index/ru.qatools.commons:properties)

This library provides convenient way to work with properties. It can handle property-files
on hard drive, in classpath or get values from system properties.

## Getting started

To add Properties to your Java project, put the following in the
dependencies section of your POM:

Latest stable version:
```xml
<dependency>
    <groupId>ru.qatools.commons</groupId>
    <artifactId>properties</artifactId>
    <version>${LATEST_VERSION}</version>
</dependency>
```

Now you are ready to describe you configuration:

```java
public interface MyConfig {

    @Property("proxy.port")
    int getPort();

    @Property("proxy.host")
    String getHost();

}
```

Then you need to populate the configuration:

```java
MyConfig config = PropertyLoader.newInstance()
        .populate(MyConfig.class);
```

By default values `proxy.port` and `proxy.host` will be read from system properties.
If you want to read properties from file you can use `@Resource` annotation:

```java
@Resource.Classpath("proxy.properties")
public interface MyConfig {

    @Property("proxy.port")
    int getPort();

    @Property("proxy.host")
    String getHost();

}
```

There is a few available options:

* `@Resource.Classpath` finds properties file by given name in your classpath.
* `@Resource.File` finds properties file by given file path.


Put in your resources directory (mainly it `src/main/resources`) file `proxy.properties`

```properties
proxy.host=proxy.yandex.ru
proxy.port=3133
```

It's easy to override value from system properties. E.g. when you run your code with

`-Dproxy.host=ya.ru`

it overrides the default value in properties file.
