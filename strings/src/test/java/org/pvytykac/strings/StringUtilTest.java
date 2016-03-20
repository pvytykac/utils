package org.pvytykac.strings;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author paly
 * @since 20/03/2016 04:04
 */
public class StringUtilTest {

    // isEmpty
    @Test
    public void testIsEmptyNullString() throws Exception {
        isEmptyTestCase(null, true);
    }

    @Test
    public void testIsEmptyEmptyString() throws Exception {
        isEmptyTestCase("", true);
    }

    @Test
    public void testIsEmptyEmptyStringSpaces() throws Exception {
        isEmptyTestCase("   ", true);
    }

    @Test
    public void testIsEmptyEmptyStringTab() throws Exception {
        isEmptyTestCase("\t", true);
    }

    @Test
    public void testIsEmptyFalse() throws Exception {
        isEmptyTestCase("test", false);
    }

    private void isEmptyTestCase(String string, boolean expected) {
        boolean empty = StringUtil.isEmpty(string);
        assertEquals(expected, empty);
    }

    // isNotEmpty
    @Test
    public void testIsNotEmptyNullString() throws Exception {
        isNotEmptyTestCase(null, false);
    }

    @Test
    public void testIsNotEmptyEmptyString() throws Exception {
        isNotEmptyTestCase("", false);
    }

    @Test
    public void testIsNotEmptyEmptyStringSpaces() throws Exception {
        isNotEmptyTestCase("   ", false);
    }

    @Test
    public void testIsNotEmptyEmptyStringTab() throws Exception {
        isNotEmptyTestCase("\t", false);
    }

    @Test
    public void testIsNotEmptyFalse() throws Exception {
        isNotEmptyTestCase("test", true);
    }

    private void isNotEmptyTestCase(String string, boolean expected) {
        boolean notEmpty = StringUtil.isNotEmpty(string);
        assertEquals(expected, notEmpty);
    }

    // concat w/o delimiter
    @Test(expected = NullPointerException.class)
    public void testConcatNullArray() throws Exception {
        concatTestCase(null, (String[]) null);
    }

    @Test
    public void testConcatNullVararg() throws Exception {
        concatTestCase("", (String) null);
    }

    @Test
    public void testConcatEmptyArray() throws Exception {
        concatTestCase("");
    }

    @Test
    public void testConcatAllDefined() throws Exception {
        concatTestCase("onetwothree", "one", "two", "three");
    }

    @Test
    public void testConcatContainsNull() throws Exception {
        concatTestCase("twothree", null, "two", "three");
    }

    @Test
    public void testConcatContainsUntrimmed() throws Exception {
        concatTestCase("onethree", "one", "   ", "three");
    }

    private void concatTestCase(String expected, String...strings) {
        String string = StringUtil.concat(strings);
        assertEquals(expected, string);
    }

    // concat /w delimiter
    @Test(expected = NullPointerException.class)
    public void testConcatDelimiterNullArray() throws Exception {
        concatDelimiterTestCase(null, "-", (String[]) null);
    }

    @Test
    public void testConcatDelimiterNullVararg() throws Exception {
        concatDelimiterTestCase("", "-", (String) null);
    }

    @Test
    public void testConcatDelimiterEmptyArray() throws Exception {
        concatDelimiterTestCase("", "-");
    }

    @Test
    public void testConcatDelimiterAllDefined() throws Exception {
        concatDelimiterTestCase("one-two-three", "-", "one", "two", "three");
    }

    @Test
    public void testConcatDelimiterContainsNull() throws Exception {
        concatDelimiterTestCase("two-three", "-", null, "two", "three");
    }

    @Test
    public void testConcatDelimiterContainsUntrimmed() throws Exception {
        concatDelimiterTestCase("one-three", "-", "one", "   ", "three");
    }

    private void concatDelimiterTestCase(String expected, String delimiter, String...strings) {
        String string = StringUtil.concat(delimiter, strings);
        assertEquals(expected, string);
    }

    // trim
    @Test
    public void testTrimNotEmpty() throws Exception {
        trimTestCase("ABCD", "  ABCD\t");
    }

    @Test
    public void testTrimNull() throws Exception {
        trimTestCase(null, null);
    }

    @Test
    public void testTrimEmpty() throws Exception {
        trimTestCase("", "");
    }

    @Test
    public void testTrimNullToEmpty() throws Exception {
        String trimmed = StringUtil.trim(null, true);
        assertEquals("", trimmed);
    }

    private void trimTestCase(String expected, String string) {
        String trimmed = StringUtil.trim(string);
        assertEquals(expected, trimmed);
    }
}
