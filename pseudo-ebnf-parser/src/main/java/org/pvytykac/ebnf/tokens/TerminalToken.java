package org.pvytykac.ebnf.tokens;

import org.pvytykac.ebnf.Token;

/**
 * @author paly
 * @since 21/09/2016 01:14
 */
public class TerminalToken implements Token {

    private final String name;

    public TerminalToken(String name) {
        this.name = name;
    }

    public String getId() {
        return name;
    }

}
