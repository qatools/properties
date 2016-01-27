package ru.qatools.properties.converters;

import org.junit.Test;
import ru.qatools.properties.DefaultValue;
import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Use;
import ru.qatools.properties.testdata.PropEnum;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author lanwen (Merkushev Kirill)
 */
public class CustomEnumConverterTest {

    @Test
    public void shouldUseRegisteredConverterForEnum() throws Exception {
        PropEnum value = PropertyLoader.newInstance()
                .register(new AlwaysGondorEnumConverter(), PropEnum.class)
                .populate(Props.class).getValue();

        assertThat("always gondor enum converter", value, is(PropEnum.GONDOR));
    }

    @Test
    public void shouldUseRegisteredInAnnotationConverterForEnum() throws Exception {
        PropEnum value = PropertyLoader.newInstance()
                .register(new AlwaysMordorEnumConverter(), PropEnum.class)
                .populate(Props.class).getAnnotatedValue();

        assertThat("always gondor enum converter win", value, is(PropEnum.GONDOR));
    }

    public interface Props {
        @Property("prop")
        @DefaultValue("unkn")
        PropEnum getValue();    
        
        @Property("prop")
        @DefaultValue("unkn")
        @Use(AlwaysGondorEnumConverter.class)
        PropEnum getAnnotatedValue();
    }

    public static class AlwaysGondorEnumConverter implements Converter<PropEnum> {
        @Override
        public PropEnum convert(String from) throws Exception {
            return PropEnum.GONDOR;
        }
    }

    public static class AlwaysMordorEnumConverter implements Converter<PropEnum> {
        @Override
        public PropEnum convert(String from) throws Exception {
            return PropEnum.MORDOR;
        }
    }
}
