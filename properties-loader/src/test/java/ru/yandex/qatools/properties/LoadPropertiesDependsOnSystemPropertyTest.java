package ru.yandex.qatools.properties;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.MultiFileProperty;
import ru.yandex.qatools.properties.testdata.MultiFilePropertyWithAnnotation;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author artkoshelev
 */
public class LoadPropertiesDependsOnSystemPropertyTest {

    @Before
    public void clearProperty() {
        System.clearProperty("property.file");
    }

    @Test
    public void loadDefaults() {
        MultiFileProperty mfp = new MultiFileProperty();
        assertThat(mfp.getProperty(), equalTo("undefined"));
    }


    @Test
    public void loadPublicProperty() {
        System.setProperty("property.file", "public.properties");
        MultiFileProperty mfp = new MultiFileProperty();
        assertThat(mfp.getProperty(), equalTo("public"));
    }

    @Test
    public void loadPrivateProperty() {
        System.setProperty("property.file", "private.properties");
        MultiFileProperty mfp = new MultiFileProperty();
        assertThat(mfp.getProperty(), equalTo("private"));
    }


    @Test
    public void loadDefaultsByAnnotation() {
        MultiFilePropertyWithAnnotation mfp = new MultiFilePropertyWithAnnotation();
        assertThat(mfp.getProperty(), equalTo("undefined"));
    }


    @Test
    public void loadPublicByAnnotation() {
        System.setProperty("property.file", "public.properties");
        MultiFilePropertyWithAnnotation mfp = new MultiFilePropertyWithAnnotation();
        assertThat(mfp.getProperty(), equalTo("public"));
    }

    @Test
    public void loadPrivateByAnnotation() {
        System.setProperty("property.file", "private.properties");
        MultiFilePropertyWithAnnotation mfp = new MultiFilePropertyWithAnnotation();
        assertThat(mfp.getProperty(), equalTo("private"));
    }

}
