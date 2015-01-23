# Configurations depending on environment

Sometimes you need to define the different configurations for different environments. Consider an example when we need a different users for authorization in tests.

First step create property file for test environment 'testing.properties':
```properties
user = Ivan
password = chelovek-divan
```
and one more file for prepoduction environment 'preprod.properties':
```properties
user = Oleg
password = chelovek-strateg
```

Then create wrapper for the class working with this properties:
```java
public class UserProperties {
	@Property("user")
	private String user = "defaultUser";
	
	@Property("password")
	private String password = "defaultPassword";
}
```

Next step add constructor for autoload properties:
```java
	public UserProperties() {
		PropertyLoader.populate(this);
	}
```

Now we need to configure *Properties* to work with different property files. Load properties from only one file so easy. Just add `@Resource.Classpath` annotation and sprcify file name:
```java
@Resource.Classpath("testing.properties")
public class UserProperties {
```

Method `PropertyLoader.populate` can accept `Properties`, which can fill our bean. By default `PropertyLoader` search for annotations `@Resource.Classpath`, `@Resource.File` and from system properties. All we need - write method reading a file which we need and returns `Properties`:
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

then we need to specify this method in loader:
```java
public UserProperties() {
	PropertyLoader.populate(this, loadProperties());
}
```

Full example of usage you can see in test `LoadPropertiesDependsOnSystemPropertyTest`.
