package ru.yandex.qatools.properties;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.qatools.properties.testdata.SupportedTypesProperties;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static ru.yandex.qatools.properties.testdata.PropEnum.valueOf;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/15/13, 8:39 PM
 */
public class SupportedTypesTest {

    public static SupportedTypesProperties properties;

    public static Properties resource;

    @BeforeClass
    public static void initProperties() throws Exception {
        resource = new Properties();
        resource.load(ClassLoader.getSystemResourceAsStream("supported-types.properties"));
        properties = new SupportedTypesProperties();
    }

    @Test
    public void booleanTypeShouldSupported() {
        assertThat(properties.getBoolean(), equalTo(Boolean.parseBoolean(resource.getProperty("supported.boolean"))));
    }

    @Test
    public void byteTypeShouldSupported() {
        assertThat(properties.getByte(), equalTo(Byte.parseByte(resource.getProperty("supported.byte"))));
    }

    @Test
    public void charTypeShouldSupported() {
        assertThat(properties.getChar(), equalTo((resource.getProperty("supported.char").charAt(0))));
    }

    @Test
    public void shortTypeShouldSupported() {
        assertThat(properties.getShort(), equalTo(Short.parseShort(resource.getProperty("supported.short"))));
    }

    @Test
    public void intTypeShouldSupported() {
        assertThat(properties.getInt(), equalTo(Integer.parseInt(resource.getProperty("supported.int"))));
    }

    @Test
    public void longTypeShouldSupported() {
        assertThat(properties.getLong(), equalTo(Long.parseLong(resource.getProperty("supported.long"))));
    }

    @Test
    public void floatTypeShouldSupported() {
        assertThat(properties.getFloat(), equalTo(Float.parseFloat(resource.getProperty("supported.float"))));
    }

    @Test
    public void doubleTypeShouldSupported() {
        assertThat(properties.getDouble(), equalTo(Double.parseDouble(resource.getProperty("supported.double"))));
    }

    @Test
    public void stringTypeShouldSupported() {
        assertThat(properties.getString(), equalTo(resource.getProperty("supported.string")));
    }

    @Test
    public void urlTypeShouldSupported() throws Exception {
        assertThat(properties.getURL(), equalTo(new URL(resource.getProperty("supported.url"))));
    }

    @Test
    public void uriTypeShouldSupported() throws Exception {
        assertThat(properties.getURI(), equalTo(URI.create(resource.getProperty("supported.uri"))));
    }


    @Test
    public void enumTypeShouldSupported() throws Exception {
        assertThat(properties.getEnum(), equalTo(valueOf(resource.getProperty("supported.enum").toUpperCase().trim())));
    }

    @Test
    public void charsetTypeShouldSupported() throws Exception {
        assertThat(properties.getCharset(), equalTo(Charset.forName(resource.getProperty("supported.charset"))));
    }
}
