## Переопределение способа загрузки пропертей

Статья решает проблему, описанную в [Загрузка кофигураций в зависимости от окружения (передача предустановленных пропертей)][create-multi-file-configuration],
но в более общем виде, позволяя аккуратно вынести грязную работу в отдельный класс,
предоставив неограниченные возможности манипуляций с пропертями.

Начальные данные:
- есть необходимость загружать разные файлы пропертей в зависимости от некоторых условий,
- путь может формироваться на лету по шаблону, используя в одном месте системные проперти, а в другом переменные из кода,
- задействует это сразу множество классов-пропертей,
- вдруг может понадобиться возможность игнорирования системных пропертей.

Стратегия загрузки пропертей реализуется при помощи классов-имплементаций для ``PropertyProvider``, у которого нужно реализовать
метод ``<T> Properties provide(T bean, Properties properties)``. Куда на вход соответственно передается бин пропертей и `Map` из
предустановленного набора пар ключ-значение.

Когда в конструкторе класса пропертей делается ``PropertyLoader.populate(this);`` или ``PropertyLoader.populate(this, someProperties);``,
у класса ищется аннотация ``@With`` со значением проперти-провайдера. В случае, если такой аннотации нет, используется дефолтный провайдер,
который ищет аннотации ``@Resource.Classpath``, ``@Resource.File`` и берет путь загрузки по их значению, а так же считывает свойства из переменных окружения.

Все что описано дальше, уже реализовано в классе ``MapOrSyspropPathReplacerProvider``, который можно подключать и превращать пути
вида
```java
@Resource.Classpath("${system.file.name}.path.${map.scope.value}.properties")
```
в реальные пути к файлам пропертей. О том, как создать свой механизм, на примере этого класса - ниже.

Попробуем реализовать возможность формирования пути файла при помощи переменных окружения, указывая место куда нужно вставить значение прямо в пути файла.
Например, нужно считывать значение системной проперти *scope.value* и добавлять ее после имени файла с пропертями.
Тогда укажем путь считывания в виде значения аннотации ``@Resource.Classpath("file.${system.scope.value}.properties")``.
Теперь, нужно искать в этом пути все строки вида `${system.*}` и заменять их на значение.

У ``DefaultPropertyProvider`` есть специальный метод получения пути из аннотации. Создадим наследника, в котором переопределим этот метод.

```java
public class SyspropPathReplacerProvider extends DefaultPropertyProvider {

    public static final String SYS_PROP_KEY_PATTERN = "\\$\\{system\\.([^\\}]*)\\}";

    @Override
    public String classpath(Class<?> clazz, Properties properties) {
        return replaceWithSystemProps(super.classpath(clazz, properties));
    }

    private String replaceWithSystemProps(String path) {
        Matcher matcher = Pattern.compile(SYS_PROP_KEY_PATTERN).matcher(path);
        while (matcher.find()) {
            path = path.replace(matcher.group(0), System.getProperty(matcher.group(1), ""));
        }
        return path;
    }
}
```

Реализация элементарна - пробегаем по строке пути, попутно заменяя по регулярному выражению все что требуется.

Теперь, подключив к классу пропертей этот провайдер, можно динамически формировать путь, просто указав куда нужно вставить значение.

```java
@Resource.Classpath("file.${system.scope.value}.properties")
@With(SyspropPathReplacerProvider.class)
public class UseSystemReplacerProviderProperty {

	public UseSystemReplacerProviderProperty() {
		PropertyLoader.populate(this);
	}

	@Property("property")
	private String property = "undefined";

	public String getProperty() {
        return property;
	}
}
```

Похожим образом, можно сделать возможность формирования пути прямо в коде, не используя переменных окружения.
Для этого нам понадобится метод ``PropertyLoader.populate(this, someProperties);``. Properties - это наследник ``Map``,
поэтому мы можем сразу передать туда необходимые ключи, которые будут доступны в провайдере.
Код пропертей будет выглядеть примерно так:

```java
@Resource.Classpath("file.${map.scope.value}.properties")
@With(MapPathReplacerProvider.class)
public class UseMapReplacerProviderProperty {

	public UseMapReplacerProviderProperty(String scope) {
        Properties map = new Properties();
        map.put("scope.value", scope);
		PropertyLoader.populate(this, map);
	}

	...

```

Тогда код провайдера изменится следующим образом:

```java
    @Override
    public String classpath(Class<?> clazz, Properties properties) {
        return replaceWithMapProps(super.classpath(clazz, properties), properties);
    }

    private String replaceWithMapProps(String path, Properties properties) {
        Matcher matcher = Pattern.compile(MAP_PROP_KEY_PATTERN).matcher(path);
        while (matcher.find()) {
            path = path.replace(matcher.group(0), properties.getProperty(matcher.group(1), ""));
        }
        return path;
    }
```

Соответственно вызов конструктора с нужным параметром и определит имя файла.
Рабочие примеры кода можно посмотреть в тесте ``CustomPropertyProviderTest``

[create-multi-file-configuration]: https://github.com/yandex-qatools/properties/blob/master/properties-loader/src/site/create-multi-file-configuration.ru.md


