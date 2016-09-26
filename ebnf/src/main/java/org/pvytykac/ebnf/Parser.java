package org.pvytykac.ebnf;

import org.pvytykac.ebnf.io.DelimitedReader;
import org.pvytykac.ebnf.tokens.Rule;

import java.util.List;

/**
 * @author paly
 * @since 21. 9. 2016
 */
public interface Parser {

    List<Rule> parse(DelimitedReader reader);

}
