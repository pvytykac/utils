package org.pvytykac.ebnf.tokens;

import org.pvytykac.ebnf.Token;

/**
 * @author paly
 * @since 21/09/2016 01:15
 */
public class NonTerminalToken implements Token {

    private final String token;

    public NonTerminalToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
