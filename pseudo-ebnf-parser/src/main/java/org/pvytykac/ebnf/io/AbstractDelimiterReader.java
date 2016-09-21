package org.pvytykac.ebnf.io;

import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 23:39
 */
public abstract class AbstractDelimiterReader implements DelimitedReader {

    @Override
    public String readUntil(char delimiter) {
        StringBuilder buff = new StringBuilder();
        char cur;

        while ((cur = next()) != delimiter) {
            buff.append(cur);
        }

        return buff.toString();
    }

    @Override
    public ReadResult readUntil(char...delimiters) {
        if (delimiters.length < 1)
            throw new IllegalArgumentException("no delimiters specified");

        StringBuilder buffer = new StringBuilder();
        List<Character> list = new CharacterList(delimiters);
        char cur;

        while(!list.contains(cur = next())) {
            buffer.append(cur);
        }

        return new ReadResult(cur, buffer.toString());
    }
}
