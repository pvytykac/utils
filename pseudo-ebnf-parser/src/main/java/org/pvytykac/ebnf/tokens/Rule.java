package org.pvytykac.ebnf.tokens;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 01:09
 */
public class Rule {

    private final String name;
    private final List<Substitution> substitutions;

    public Rule(String name, List<Substitution> substitutions) {
        this.name = name;
        this.substitutions = new LinkedList<>(substitutions);
    }

    public String getName() {
        return name;
    }

    public Iterator<Substitution> getSubstitutions() {
        return substitutions.iterator();
    }
}
