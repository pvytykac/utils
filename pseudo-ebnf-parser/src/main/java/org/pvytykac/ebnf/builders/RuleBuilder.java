package org.pvytykac.ebnf.builders;

import org.pvytykac.ebnf.tokens.Rule;
import org.pvytykac.ebnf.tokens.Substitution;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 22:35
 */
public class RuleBuilder {

    private String name;
    private List<Substitution> substitutions = new LinkedList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void addSubstitution(Substitution substitution) {
        this.substitutions.add(substitution);
    }

    public Rule build() {
        return new Rule(name, substitutions);
    }

}
