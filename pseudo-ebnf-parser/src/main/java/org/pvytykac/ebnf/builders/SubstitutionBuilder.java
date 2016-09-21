package org.pvytykac.ebnf.builders;

import org.pvytykac.ebnf.Token;
import org.pvytykac.ebnf.tokens.Substitution;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 22:36
 */
public class SubstitutionBuilder {

    private List<Token> tokens = new LinkedList<>();

    public void addToken(Token token) {
        tokens.add(token);
    }

    public Substitution build() {
        return new Substitution(tokens);
    }

}
