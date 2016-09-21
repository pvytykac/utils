package org.pvytykac.ebnf.tokens;

import org.pvytykac.ebnf.Token;

import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 01:11
 */
public class Substitution {

    private List<Token> tokens;

    public Substitution(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Token> getTokens() {
        return tokens;
    }

}
