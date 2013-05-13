# Создание собственного конвертера

Иногда необходимо использовать типы данных, отличные от стандартных:
- boolean
- byte
- short
- int
- long
- double
- float
- char
- string

Для примера, представим, что нам необходимо инициализировать список в проперти классе.
Для этого создадим файл `servers.properties` со следующим содержанием:
```property
servers.list=yandex.ru, auto.yandex.ru, mail.yandex.ru
```

Теперь создадим класс-обертку, который будет использовать данные из файла:

```java
@Resource.Classpath("servers.list")
public class ServersProperties {

    @Property("servers.list")
    private List<String> servers;

    public ServersProperties () {
        PropertyLoader.populate(this);
    }

    public List<String> getServers() {
        return servers;
    }
}
```

Если попробовать инициализировать этот класс, то вы увидите эксепшен. Чтобы использование списка стало возможным,
необходимо зарегистрировать создать свой конвертер и зарегистрировать его.

## Имплементация интерфейса org.apache.commons.beanutils.Converter

В основе property-loader лежит библиотека
[Apache BeanUtils](http://commons.apache.org/proper/commons-beanutils/).
Эта библиотека имеет очень удобный интрефейс регистрации новых конвертеров. Происходит это следующим образом.
Создаем свой конверер:

```java
public class ListConverter implements Converter {
    private String delimiter;

    public ListConverter () {
        this(", ");
    }

    public ListConverter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Object convert(Class aClass, Object o) {
        if (!(o instanceof String)) return new ArrayList<String>();
        String str = (String) o;
        return Arrays.asList(str.split(delimiter));
    }
}
```

Теперь нужно указать какой конвертер использоваь для обработки конкретного поля класса.

## Регистрация конвертера

```java
@Resource.Classpath("servers.properties")
public class ServersProperties {

    public ServersProperties() {
        PropertyLoader.populate(this);
    }

    @Use(ListConverter.class) //конвертер, который необходимо использовать
    @Property("servers.list") //ключ проперти, значение которого будет списком
    private List<String> servers;

    public List<String> getServers() {
        return servers;
    }
}
```

Таким образом можно переопределять существующие конвертеры и добавлять новые.