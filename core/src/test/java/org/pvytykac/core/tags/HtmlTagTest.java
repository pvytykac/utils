package org.pvytykac.core.tags;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author paly
 * @since 17/11/2016 10:44
 */
public class HtmlTagTest {

    @Test
    public void testNoAttributesNoBody() throws Exception {
        Tag tag = new HtmlTag("span");
        assertEquals("<span/>", tag.full());
        assertEquals("<span>", tag.open());
        assertEquals("</span>", tag.close());
    }

    @Test
    public void testAttributesAndStringBody() throws Exception {
        Tag tag = new HtmlTag("input")
            .id("id-confirm-chk")
            .attr("type","checkbox")
            .attr("name", "confirmation")
            .attr("required")
            .body("I agree");

        String full = "<input id=\"id-confirm-chk\" type=\"checkbox\" name=\"confirmation\" required>I agree</input>";
        String open = "<input id=\"id-confirm-chk\" type=\"checkbox\" name=\"confirmation\" required>I agree";
        String close = "</input>";

        assertEquals(full, tag.full());
        assertEquals(open, tag.open());
        assertEquals(close, tag.close());
    }

    @Test
    public void testAttributesAndHtmlBody() throws Exception {
        Tag tag = new HtmlTag("a")
            .attr("class", "img-link")
            .attr("href", "/images/pic.png")
            .body(
                new HtmlTag("img")
                    .attr("src", "/images/pic.png")
            );

        String full = "<a class=\"img-link\" href=\"/images/pic.png\"><img src=\"/images/pic.png\"/></a>";
        String open = "<a class=\"img-link\" href=\"/images/pic.png\"><img src=\"/images/pic.png\">";
        String close = "</img></a>";
        assertEquals(full, tag.full());
        assertEquals(open, tag.open());
        assertEquals(close, tag.close());
    }

    @Test
    public void testNullAttributes() throws Exception {
        HtmlTag tag = new HtmlTag("span")
            .attr(null)
            .id(null)
            .attr("class", null);

        assertEquals("<span/>", tag.full());
        assertEquals("<span>", tag.open());
        assertEquals("</span>", tag.close());
    }

    @Test
    public void testNullTextBody() throws Exception {
        HtmlTag tag = new HtmlTag("span")
            .body((String) null);

        assertEquals("<span/>", tag.full());
        assertEquals("<span>", tag.open());
        assertEquals("</span>", tag.close());
    }

    @Test
    public void testNullHtmlBody() throws Exception {
        HtmlTag tag = new HtmlTag("span")
            .body((HtmlTag) null);

        assertEquals("<span/>", tag.full());
        assertEquals("<span>", tag.open());
        assertEquals("</span>", tag.close());
    }

    @Test
    public void testMultipleBodyParts() throws Exception {
        Tag tag = new HtmlTag("table")
            .body(new HtmlTag("thead"))
            .body(new HtmlTag("tbody"));

        String full = "<table><thead/><tbody/></table>";
        assertEquals(full, tag.full());

        try {
            tag.open();
        } catch (IllegalStateException ignored) {
        } catch (Exception ex) {
            fail("Expected open() to throw an IllegalStateException");
        }

        try {
            tag.close();
        } catch (IllegalStateException ignored) {
        } catch (Exception ex) {
            fail("Expected close() to throw an IllegalStateException");
        }
    }

}
