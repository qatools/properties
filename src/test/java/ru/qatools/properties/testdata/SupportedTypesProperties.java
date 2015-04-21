package ru.qatools.properties.testdata;

import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.annotations.Property;
import ru.qatools.properties.annotations.Resource;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/15/13, 8:21 PM
 */
@Resource.Classpath("supported-types.properties")
public class SupportedTypesProperties {

    @Property("supported.double")
    private double aDouble;

    @Property("supported.float")
    private float aFloat;

    @Property("supported.short")
    private short aShort;

    @Property("supported.int")
    private int aInt;

    @Property("supported.byte")
    private byte aByte;

    @Property("supported.string")
    private String aString;

    @Property("supported.boolean")
    private boolean aBoolean;

    @Property("supported.long")
    private long aLong;

    @Property("supported.char")
    private char aChar;

    @Property("supported.url")
    private URL aURL;

    @Property("supported.uri")
    private URI aURI;

    @Property("supported.enum")
    private PropEnum anEnum;

    @Property("supported.charset")
    private Charset aCharset;

    @Property("supported.invalid.charset")
    private Charset anInvalidCharset;

    public SupportedTypesProperties() {
        PropertyLoader.newInstance().populate(this);
    }

    public double getDouble() {
        return aDouble;
    }

    public float getFloat() {
        return aFloat;
    }

    public short getShort() {
        return aShort;
    }

    public int getInt() {
        return aInt;
    }

    public long getLong() {
        return aLong;
    }

    public byte getByte() {
        return aByte;
    }

    public boolean getBoolean() {
        return aBoolean;
    }

    public String getString() {
        return aString;
    }

    public char getChar() {
        return aChar;
    }

    public URL getURL() {
        return aURL;
    }

    public URI getURI() {
        return aURI;
    }

    public PropEnum getEnum() {
        return anEnum;
    }

    public Charset getCharset() {
        return aCharset;
    }

    public Charset getInvalidCharset() {
        return anInvalidCharset;
    }
}
