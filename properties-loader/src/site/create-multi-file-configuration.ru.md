# Загрузка кофигураций  в зависимости от окружения

Иногда необходимо подключать различные файлы конфигураций для тестирования на разных тестовых средах.
Допустим нам нужны разные пользователи для авторизации в тестах. 

Создадим проперти-файл для тестового окружения 'testing.properties':
```properties
user = Ivan
password = chelovek-divan
```
и для предпродакшн окружения 'preprod.properties':
```properties
user = Oleg
password = chelovek-strateg
```

Создадим класс-обёртку, работающий с этими данными:
```java
public class UserProperties {
	@Property("user")
	private String user = "defaultUser";
	
	@Property("password")
	private String password = "defaultPassword";
}
```

Для того, чтобы проперти автоматически подгружались при создании этого класса, нужно добавить конструктор:
```java
	public UserProperties() {
		PropertyLoader.populate(this);
	}
```

Осталось научить наш класс работать с разными файлами. Загружать проперти из одного файла очень просто - достаточно
добавить аннотацию @Resource.Classpath в которой указать имя этого файла:
```java
@Resource.Classpath("testing.properties")
public class UserProperties {
```

Метод PropertyLoader.populate может принимать на вход Properties, которыми нужно заполнить наш бин. По дефолту,
PropertyLoader ищет аннотации @Resource.Classpath, @Resource.File, а так же считывает свойства из переменных окружения.
Нам остётся написать метод, который будет возвращать Properties, считаный из нужного нам файла:
```java
	private static Properties loadProperties() {
		Properties result = new Properties();
		String filePath = System.getProperty("property.file");
		if (filePath != null) {
			result.putAll(readProperties(ClassLoader.getSystemResourceAsStream(filePath)));
		}
		return result;
	}
```

и передать этот метод в загрузчик при инициализации нашего класса:
```java
	public MultiFileProperty() {
		PropertyLoader.populate(this, loadProperties());
	}
```

Полный пример реализации можно посмотреть в тесте LoadPropertiesDependsOnSystemPropertyTest.