package ru.yandex.qatools.properties;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import ru.yandex.qatools.properties.testdata.MultiFileProperty;

/**
 * 
 * @author artkoshelev
 *
 */
public class LoadPropertiesDependsOnSystemPropertyTest {

	@Test
	public void loadDefaults() {
		MultiFileProperty mfp = new MultiFileProperty();
		assertThat(mfp.getProperty(), equalTo("undefined"));
	}
	
	@Test
	public void loadPublicProperty() {
		System.setProperty("property.file", "public.properties");
		MultiFileProperty mfp= new MultiFileProperty();
		assertThat(mfp.getProperty(), equalTo("public"));
	}
	
	@Test
	public void loadPrivateProperty() {
		System.setProperty("property.file", "private.properties");
		MultiFileProperty mfp = new MultiFileProperty();
		assertThat(mfp.getProperty(), equalTo("private"));
	}
	
}
