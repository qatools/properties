package ru.qatools.properties.testdata;

import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Property;
import ru.qatools.properties.Resource;

import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Artem Eroshenko eroshenkoam
 *         3/15/13, 8:21 PM
 */
@Resource.Classpath("supported-types.properties")
public class SupportedTypesProperties {

    @Property("supported.double")
    public double aDouble;

    @Property("supported.float")
    public float aFloat;

    @Property("supported.short")
    public short aShort;

    @Property("supported.int")
    public int anInt;

    @Property("supported.int.array")
    public int[] intArray;

    @Property("supported.byte")
    public byte aByte;

    @Property("supported.string")
    public String string;

    @Property("supported.boolean")
    public boolean aBoolean;

    @Property("supported.long")
    public long aLong;

    @Property("supported.char")
    public char aChar;

    @Property("supported.url")
    public URL url;

    @Property("supported.uri")
    public URI uri;

    @Property("supported.enum")
    public PropEnum anEnum;

    @Property("supported.charset")
    public Charset charset;

    @Property("supported.collection")
    public Collection<String> collection;

    @Property("supported.collection")
    public Collection collectionWithoutGeneric;

    @Property("supported.collection")
    public List listWithoutGeneric;

    @Property("supported.collection")
    public List<String> stringList;

    @Property("supported.collection")
    public Set<String> stringSet;

    @Property("supported.collection")
    public HashSet<String> stringHashSet;

    @Property("supported.collection")
    public MySet<String> stringCustomSet;

    public SupportedTypesProperties() {
        PropertyLoader.newInstance().populate(this);
    }

    public static class MySet<T> extends HashSet<T> {
        public MySet(Collection<? extends T> c) {
            super(c);
        }
    }

}
