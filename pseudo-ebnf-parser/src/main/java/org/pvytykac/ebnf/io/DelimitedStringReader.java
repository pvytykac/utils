package org.pvytykac.ebnf.io;

/**
 * @author paly
 * @since 21/09/2016 22:49
 */
// TODO: implement eos handling after stream reader is finished
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
