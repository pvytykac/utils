package org.pvytykac.ebnf;

import org.pvytykac.ebnf.io.DelimitedReader;

/**
 * @author paly
 * @since 21. 9. 2016
 */
public interface ParserState {

    void offer(StatefulParser parser, DelimitedReader input);

}
