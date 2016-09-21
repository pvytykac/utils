package org.pvytykac.ebnf.io;

/**
 * @author paly
 * @since 21/09/2016 23:39
 */
public abstract class AbstractDelimiterReader implements DelimitedReader {

    @Override
    public String readUntil(char delimiter) {
        return null;
    }

    @Override
    public ReadResult readUntil(char... delimiters) {
        return null;
    }
}
