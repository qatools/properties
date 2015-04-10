package ru.qatools.properties;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.qatools.properties.testdata.MultiFileProperty;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author artkoshelev
 */
public class LoadPropertiesDependsOnSystemPropertyTest {


    @BeforeClass
    public static void clear() throws Exception {
        System.clearProperty("property");
    }

    @Test
    public void loadDefaults() {
        System.clearProperty("property.file");
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

}
