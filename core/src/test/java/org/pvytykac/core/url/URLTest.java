package org.pvytykac.core.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.internal.matchers.LessThan;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.doThrow;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author paly
 * @since 17/11/2016 10:44
 */
@RunWith(PowerMockRunner.class)
public class URLTest {

    public static final String BASE = "testPage.html";
    public static final String ANCHOR = "item-56";
    public static final String PARAMETER = "forwarded-from";
    public static final String VALUE = "http://testurl.com/index.html?ref=fb&id=123";

    @Test
    public void testToStringBaseOnly() throws Exception {
        URL url = new URL(BASE);

        assertEquals(BASE, url.toString());
    }

    @Test
    public void testToStringAnchorOnly() throws Exception {
        URL url = new URL().anchor(ANCHOR);

        assertEquals("#" + ANCHOR, url.toString());
    }

    @Test
    public void testToStringParameterOnly() throws Exception {
        URL url = new URL().parameter(PARAMETER, VALUE);
        String expected = "?" + URLEncoder.encode(PARAMETER, "utf-8") + "=" + URLEncoder.encode(VALUE, "utf-8");

        assertEquals(expected, url.toString());
    }

    @Test
    public void testToStringNullParameter() throws Exception {
        URL url = new URL().parameter(PARAMETER, null);

        assertEquals("", url.toString());
    }

    @Test
    public void testToStringMatchedParameters() throws Exception {
        int val = 10;
        URL url = new URL()
            .parameter("a", val, new GreaterThan<>(25))
            .parameter("b", val, new LessThan<>(25));

        assertEquals("?b=10", url.toString());
    }

    @Test
    @PrepareForTest({URLEncoder.class, URL.class})
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void testToStringUnsupportedEncoding() throws Exception {
        boolean caught = false;

        try {
            mockStatic(URLEncoder.class);
            doThrow(new UnsupportedEncodingException()).when(URLEncoder.class);
            URLEncoder.encode(anyString(), anyString());

            new URL().parameter("a", "b").toString();
        } catch (RuntimeException ex) {
            assertTrue(ex.getCause() instanceof UnsupportedEncodingException);
            caught = true;
        }

        if (!caught)
            fail("expected UnsupportedEncodingException wrapped in RuntimeException");
    }
}
