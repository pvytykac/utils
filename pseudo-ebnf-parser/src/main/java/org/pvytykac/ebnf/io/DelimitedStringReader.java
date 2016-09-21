package org.pvytykac.ebnf.io;

/**
 * TODO: implement eos handling after stream reader is finished
 * @author paly
 * @since 21/09/2016 22:49
 */
public final class DelimitedStringReader extends AbstractDelimiterReader {

    private final String string;
    private int position = 0;

    public DelimitedStringReader(String string) {
        this.string = string;
    }

    @Override
    public char next() {
        return string.charAt(position++);
    }
}
