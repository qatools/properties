package ru.yandex.qatools.properties;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.UseMapReplacerProviderProperty;
import ru.yandex.qatools.properties.testdata.UseSystemReplacerProviderProperty;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * User: lanwen
 * Date: 17.07.13
 * Time: 20:17
 */
public class CustomPropertyProviderTest {

    @BeforeClass
    public static void clear() throws Exception {
        System.clearProperty("property");
    }

    @Test
    public void loadDefaults() {
        System.clearProperty("file.name");
        System.clearProperty("scope.value");
        UseSystemReplacerProviderProperty mfp = new UseSystemReplacerProviderProperty();
        assertThat(mfp.getProperty(), equalTo("undefined"));
    }

    @Test
    public void loadTestingProperty() {
        System.setProperty("file.name", "first");
        System.setProperty("scope.value", "testing");
        UseSystemReplacerProviderProperty mfp = new UseSystemReplacerProviderProperty();
        assertThat(mfp.getProperty(), equalTo("testing"));
    }

    @Test
    public void loadProductionProperty() {
        System.setProperty("file.name", "second");
        System.setProperty("scope.value", "production");
        UseSystemReplacerProviderProperty mfp = new UseSystemReplacerProviderProperty();
        assertThat(mfp.getProperty(), equalTo("production"));
    }

    @Test
    public void noFileLoadDefaults() {
        UseMapReplacerProviderProperty mfp = new UseMapReplacerProviderProperty("third", "beta");
        assertThat(mfp.getProperty(), equalTo("undefined"));
    }

    @Test
    public void loadTestingPropertyWithMap() {
        UseMapReplacerProviderProperty mfp = new UseMapReplacerProviderProperty("first", "testing");
        assertThat(mfp.getProperty(), equalTo("testing"));
    }

    @Test
    public void loadProductionPropertyWithMap() {
        UseMapReplacerProviderProperty mfp = new UseMapReplacerProviderProperty("second", "production");
        assertThat(mfp.getProperty(), equalTo("production"));
    }
}
