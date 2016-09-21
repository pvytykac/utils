package org.pvytykac.ebnf.io;

/**
 * @author paly
 * @since 21/09/2016 22:41
 */
public final class ReadResult {

    private final char delimiter;
    private final String read;

    public ReadResult(char delimiter, String read) {
        this.delimiter = delimiter;
        this.read = read;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public String getRead() {
        return read;
    }

}
