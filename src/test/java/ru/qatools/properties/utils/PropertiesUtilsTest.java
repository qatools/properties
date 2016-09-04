package ru.qatools.properties.utils;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitriy Polyanov <a href="mailto:neiwick@yandex-team.ru"></a>
 *         Date: 29.08.16
 */
public class PropertiesUtilsTest {

    @Test
    public void shouldCloseInputStream() throws Exception {
        EmptyInputStreamStub inputStream = new EmptyInputStreamStub();
        PropertiesUtils.readProperties(inputStream);

        assertThat(inputStream.closed, is(true));
    }

    private class EmptyInputStreamStub extends InputStream {
        private boolean closed = false;

        @Override
        public int read() throws IOException {
            return -1;
        }

        @Override
        public void close() throws IOException {
            closed = true;
            super.close();
        }
    }
}