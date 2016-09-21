package org.pvytykac.ebnf.io;

/**
 * @author paly
 * @since 21. 9. 2016
 */
public interface DelimitedReader {

    /**
     * reads the character at the current position
     * @return the next character
     */
    char next();

    /**
     * reads the input from the current position until the delimiter is encoutered
     * @param delimiter character to stop reading on
     * @return String read from the current position (inclusive) until the delimiter (exclusive)
     */
    String readUntil(char delimiter);

    /**
     * reads the input from the current position until one of the provided delimiters was read
     *
     * @param delimiters vararg representing the delimiters to read until
     * @return {@link ReadResult} object containing the read string and the encountered delimiter
     */
    ReadResult readUntil(char...delimiters);

}
