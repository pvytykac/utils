package org.pvytykac.ebnf.io;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author paly
 * @since 21/09/2016 23:07
 */
// TODO: add more test cases (ie. current position == delimiter, end of stream once its implemented, etc.)
public class DelimitedStringReaderTest {

    private DelimitedStringReader reader;
    private static String input = "abcdef#ghijklmno%pqrstuv$$xyz.";

    @Before
    public void setUp() throws Exception {
        reader = new DelimitedStringReader(input);
    }

    @Test
    public void testNext() throws Exception {
        for (int ix = 0; ix < input.length(); ix++) {
            char expected = input.charAt(ix);
            char read = reader.next();
            assertEquals(expected, read);
        }
    }

    @Test
    public void testReadUntilHash() throws Exception {
        String read = reader.readUntil('#');
        String expected = input.split("#")[0];

        assertEquals(expected, read);
    }

    @Test
    public void testReadUntilHashOrPercent() throws Exception {
        int ix = Math.min(input.indexOf('%'), input.indexOf('#'));
        ReadResult result = reader.readUntil('%', '#');
        String expectedString = input.substring(0, ix);
        char expectedDelimiter = input.charAt(ix);

        assertNotNull(result);
        assertEquals(expectedString, result.getRead());
        assertEquals(expectedDelimiter, result.getDelimiter());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadUntilNoDelimiters() throws Exception {
        reader.readUntil();
    }
}
