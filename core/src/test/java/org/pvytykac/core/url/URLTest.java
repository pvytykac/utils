package org.pvytykac.core.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.pvytykac.core.url.URL.url;

/**
 * @author paly
 * @since 17/11/2016 10:44
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({URLEncoder.class, URL.class})
public class URLTest {

    private static final String EMPTY_URL = "?";
    private static final URL URL = url();

    @Test
    public void testUrlNoBase() throws Exception {
        URL url = url();

        assertNotNull(url);
        assertNull(url.getBase());
        assertNull(url.getAnchor());
        assertNull(url.getParameters());
        assertEquals(EMPTY_URL, url.toString());
    }

    @Test
    public void testUrlBase() throws Exception {
        String base = "inbox.html";
        URL url = url(base);

        assertNotNull(url);
        assertNull(url.getAnchor());
        assertNull(url.getParameters());
        assertEquals(base, url.getBase());
        assertEquals(base, url.toString());
    }

    @Test
    public void testAnchorNullToSome() throws Exception {
        String anchor = "id-123";
        URL result = URL.anchor(anchor);

        assertAnchor(result, URL, anchor, false);
    }

    @Test
    public void testAnchorNullToNull() throws Exception {
        assertAnchor(URL.anchor(null), URL, null, true);
    }

    @Test
    public void testAnchorNullToEmpty() throws Exception {
        String anchor = "";
        assertAnchor(URL.anchor(anchor), URL, anchor, true);
    }

    @Test
    public void testAnchorEmptyToSome() throws Exception {
        String anchor = "id-123";
        URL original = URL.anchor("");
        URL result = original.anchor(anchor);

        assertAnchor(result, original, anchor, false);
    }

    @Test
    public void testAnchorEmptyToNull() throws Exception {
        URL original = URL.anchor("");
        URL result = original.anchor(null);
        assertAnchor(result, original, "", true);
    }

    @Test
    public void testAnchorEmptyToEmpty() throws Exception {
        URL original = URL.anchor("");
        URL result = original.anchor("");
        assertAnchor(result, original, "", true);
    }

    @Test
    public void testAnchorSomeToDifferent() throws Exception {
        String anchorA = "id-123";
        String anchorB = "id-234";
        URL original = URL.anchor(anchorA);
        URL result = original.anchor(anchorB);

        assertAnchor(result, original, anchorB, false);
    }

    @Test
    public void testAnchorSomeToSame() throws Exception {
        String anchor = "id-123";
        URL original = URL.anchor(anchor);
        URL result = original.anchor(anchor);

        assertAnchor(result, original, anchor, true);
    }

    @Test
    public void testAnchorSomeToNull() throws Exception {
        String anchor = "id-123";
        URL original = URL.anchor(anchor);
        URL result = original.anchor(null);

        assertAnchor(result, original, null, false);
    }

    @Test
    public void testAnchorSomeToEmpty() throws Exception {
        String anchor = "id-123";
        URL original = URL.anchor(anchor);
        URL result = original.anchor("");

        assertAnchor(result, original, "", false);
    }

    @Test
    public void testParameterKeyValueOverload() throws Exception {
        URL spy = spy(URL);
        spy.parameter(null, null);

        verify(spy, times(1)).parameter(null, null, true);
    }

    @Test
    public void testParameterNullKeyNullValue() throws Exception {
        URL result = URL.parameter(null, null, true);
        assertParameter(result, URL, null, null, true);
    }

    @Test
    public void testParameterEmptyKeyEmptyValue() throws Exception {
        URL result = URL.parameter("", "", true);
        assertParameter(result, URL, "", "", true);
    }

    @Test
    public void testParameterSomeKeyNullValue() throws Exception {
        String key = "id";
        URL result = URL.parameter(key, null, true);
        assertParameter(result, URL, key, null, true);
    }

    @Test
    public void testParameterSomeKeySomeValue() throws Exception {
        String key = "id";
        String value = "123";
        URL result = URL.parameter(key, value, true);
        assertParameter(result, URL, key, value, false);
    }

    @Test
    public void testParameterSomeKeySomeValueConditionFalse() throws Exception {
        String key = "id";
        String value = "123";
        URL result = URL.parameter(key, value, false);
        assertParameter(result, URL, null, null, true);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void testAdvancedUseCase() throws Exception {
        int page = 2;
        int prev = 25;
        int next = 75;
        int offsetId = 12345;
        boolean inclusive = false;
        int defaultPageSize = 50;
        String filter = "+ľščťýľíáýť";

        URL url = url("index.php")
            .anchor("table")
            .parameter("filter", filter)
            .parameter("page", page, page > 0)
            .parameter("offsetId", offsetId, offsetId > 0)
            .parameter("next", next, next > 0 && (next != defaultPageSize || prev > 0))
            .parameter("prev", prev, prev > 0)
            .parameter("inclusive", inclusive, inclusive);

        String expected = String.format("index.php?filter=%s&page=%d&offsetId=%d&next=%d&prev=%d#table",
            URLEncoder.encode(filter, "utf8"), page, offsetId, next, prev);
        assertEquals(expected, url.toString());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testParamMapImmutable() throws Exception {
        URL url = URL.parameter("testA", "testA");
        url.getParameters().put("testB", "testB");
    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void testToStringUnsupportedEncoding() throws Exception {
        boolean caught = false;

        try {
            mockStatic(URLEncoder.class);
            doThrow(new UnsupportedEncodingException()).when(URLEncoder.class);
            URLEncoder.encode(anyString(), anyString());

            url().parameter("a", "b").toString();
            fail("expected UnsupportedEncodingException wrapped in RuntimeException");
        } catch (RuntimeException ex) {
            assertTrue(ex.getCause() instanceof UnsupportedEncodingException);
        }
    }

    private static void assertAnchor(URL result, URL original, String anchor, boolean same) {
        String expected = (anchor == null || anchor.isEmpty())
            ? EMPTY_URL
            : "#" + anchor;

        assertNotNull(result);
        assertEquals(expected, result.toString());
        assertURLSame(result, original, same);
    }

    private static void assertParameter(URL result, URL original, String key, String value, boolean same) {
        String expected = (key == null || key.isEmpty() || value == null)
            ? EMPTY_URL
            : "?" + key + "=" + value;

        assertNotNull(result);
        assertEquals(expected, result.toString());
        assertURLSame(result, original, same);
    }

    private static void assertURLSame(URL result, URL original, boolean same) {
        if (same)
            assertSame(original, result);
        else
            assertNotSame(original, result);
    }
}