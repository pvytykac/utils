package org.pvytykac.ebnf;

import org.pvytykac.ebnf.tokens.Rule;

/**
 * @author paly
 * @since 21. 9. 2016
 */
public interface StatefulParser {

    void writeResult(Rule rule);
    void setState(ParserState state);

}
