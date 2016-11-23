package org.pvytykac.core.tags;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.pvytykac.core.tags.Tag.tag;

/**
 * @author paly
 * @since 17/11/2016 10:44
 */
public class TagTest {

    private static final String TAG_NAME = "input";
    private static final Tag TAG = tag(TAG_NAME);
    private static final String TAG_FULL = "<" + TAG_NAME + "/>";
    private static final String TAG_OPEN = "<" + TAG_NAME + ">";
    private static final String TAG_CLOSE = "</" + TAG_NAME + ">";

    @Test(expected = IllegalArgumentException.class)
    public void testInitException() throws Exception {
        tag(null);
    }

    @Test
    public void testInitOk() throws Exception {
        assertTag(tag(TAG_NAME), TAG_FULL, TAG_OPEN, TAG_CLOSE);
    }

    @Test
    public void testValuelessAttrNull() throws Exception {
        Tag tag = TAG.attr(null);
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testValuelessAttrEmpty() throws Exception {
        Tag tag = TAG.attr("");
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testValuelessAttr() throws Exception {
        Tag tag = TAG.attr("required");
        String full = "<input required/>";
        String open = "<input required>";
        String close = "</input>";

        assertTag(tag, full, open, close);
    }

    @Test
    public void testValueAttrNullKey() throws Exception {
        Tag tag = TAG.attr(null, "value");
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testValueAttrEmptyKey() throws Exception {
        Tag tag = TAG.attr("", "value");
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testValueAttrNullValue() throws Exception {
        Tag tag = TAG.attr("key", null);
        String full = "<%name key=\"\"/>";
        String open = "<%name key=\"\">";
        String close = "</%name>";

        assertTag(tag, full, open, close);
    }

    @Test
    public void testValueAttrEmptyValue() throws Exception {
        Tag tag = TAG.attr("key", "");
        String full = "<%name key=\"\"/>";
        String open = "<%name key=\"\">";
        String close = "</%name>";

        assertTag(tag, full, open, close);
    }

    @Test
    public void testValueAttr() throws Exception {
        Tag tag = TAG.attr("key", "value");
        String full = "<%name key=\"value\"/>";
        String open = "<%name key=\"value\">";
        String close = "</%name>";

        assertTag(tag, full, open, close);
    }

    @Test
    public void testBodyNullElement() throws Exception {
        Tag tag = TAG.body((HtmlElement) null);
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testBodyElement() throws Exception {
        Tag tag = TAG.body(tag("span").attr("id", "12345"));
        String full = "<%name><span id=\"12345\"/></%name>";
        String open = "<%name><span id=\"12345\">";
        String close = "</span></%name>";

        assertTag(tag, full, open, close);
    }

    @Test
    public void testBodyNullString() throws Exception {
        Tag tag = TAG.body((String) null);
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testBodyEmptyString() throws Exception {
        Tag tag = TAG.body("");
        assertTagSameAsRoot(tag);
    }

    @Test
    public void testBodyString() throws Exception {
        Tag tag = TAG.body("text");
        String full = "<%name>text</%name>";
        String open = "<%name>text";
        String close = "</%name>";

        assertTag(tag, full, open, close);
    }

    @Test(expected = IllegalStateException.class)
    public void testOpenException() throws Exception {
        Tag tag = TAG
            .body(tag("span"))
            .body(tag("div"));

        tag.open();
    }

    @Test(expected = IllegalStateException.class)
    public void testCloseException() throws Exception {
        Tag tag = TAG
            .body(tag("span"))
            .body(tag("div"));

        tag.close();
    }

    private static void assertTagSameAsRoot(Tag tag) {
        assertTag(tag, TAG, TAG_FULL, TAG_OPEN, TAG_CLOSE, true);
    }

    private static void assertTag(Tag tag, String full, String open, String close) {
        assertTag(tag, TAG, full, open, close, false);
    }

    private static final String NAME_PLACEHOLDER = "%name";

    private static void assertTag(Tag current, Tag original, String full, String open, String close, boolean same) {
        full = full.replaceAll(NAME_PLACEHOLDER, current.getName());
        open = open.replaceAll(NAME_PLACEHOLDER, current.getName());
        close = close.replaceAll(NAME_PLACEHOLDER, current.getName());

        assertEquals(full, current.full());
        assertEquals(full, current.toString());
        assertEquals(open, current.open());
        assertEquals(close, current.close());

        if (same)
            assertSame(original, current);
        else
            assertNotSame(original, current);
    }

}