package ru.qatools.properties;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.qatools.properties.testdata.SupportedTypesProperties;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static ru.qatools.properties.testdata.PropEnum.valueOf;

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
        assertThat(properties.aBoolean, equalTo(Boolean.parseBoolean(resource.getProperty("supported.boolean"))));
    }

    @Test
    public void byteTypeShouldSupported() {
        assertThat(properties.aByte, equalTo(Byte.parseByte(resource.getProperty("supported.byte"))));
    }

    @Test
    public void charTypeShouldSupported() {
        assertThat(properties.aChar, equalTo((resource.getProperty("supported.char").charAt(0))));
    }

    @Test
    public void shortTypeShouldSupported() {
        assertThat(properties.aShort, equalTo(Short.parseShort(resource.getProperty("supported.short"))));
    }

    @Test
    public void intTypeShouldSupported() {
        assertThat(properties.anInt, equalTo(Integer.parseInt(resource.getProperty("supported.int"))));
    }

    @Test
    public void longTypeShouldSupported() {
        assertThat(properties.aLong, equalTo(Long.parseLong(resource.getProperty("supported.long"))));
    }

    @Test
    public void floatTypeShouldSupported() {
        assertThat(properties.aFloat, equalTo(Float.parseFloat(resource.getProperty("supported.float"))));
    }

    @Test
    public void doubleTypeShouldSupported() {
        assertThat(properties.aDouble, equalTo(Double.parseDouble(resource.getProperty("supported.double"))));
    }

    @Test
    public void stringTypeShouldSupported() {
        assertThat(properties.string, equalTo(resource.getProperty("supported.string")));
    }

    @Test
    public void urlTypeShouldSupported() throws Exception {
        assertThat(properties.url, equalTo(new URL(resource.getProperty("supported.url"))));
    }

    @Test
    public void uriTypeShouldSupported() throws Exception {
        assertThat(properties.uri, equalTo(URI.create(resource.getProperty("supported.uri"))));
    }


    @Test
    public void enumTypeShouldSupported() throws Exception {
        assertThat(properties.anEnum, equalTo(valueOf(resource.getProperty("supported.enum").toUpperCase().trim())));
    }

    @Test
    public void charsetTypeShouldSupported() throws Exception {
        assertThat(properties.charset, equalTo(Charset.forName(resource.getProperty("supported.charset"))));
    }

    @Test
    public void shouldSupportCollectionsWithGenerics() throws Exception {
        assertThat(properties.collection, hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportCollectionsWithoutGenerics() throws Exception {
        assertThat(properties.collectionWithoutGeneric, (Matcher) hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportListWithoutGenerics() throws Exception {
        assertThat(properties.listWithoutGeneric, (Matcher) hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportList() throws Exception {
        assertThat(properties.stringList, (Matcher) hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportSet() throws Exception {
        assertThat(properties.stringSet, (Matcher) hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportHashSet() throws Exception {
        assertThat(properties.stringHashSet, (Matcher) hasItems(getExpectedStrings()));
    }

    @Test
    public void shouldSupportCustomCollection() throws Exception {
        assertThat(properties.stringCustomSet, (Matcher) hasItems(getExpectedStrings()));
    }

    private String[] getExpectedStrings() {
        return resource.getProperty("supported.collection").split(",");
    }
}
