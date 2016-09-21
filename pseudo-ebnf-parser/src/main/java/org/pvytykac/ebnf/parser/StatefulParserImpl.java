package org.pvytykac.ebnf.parser;

import org.pvytykac.ebnf.Parser;
import org.pvytykac.ebnf.ParserState;
import org.pvytykac.ebnf.StatefulParser;
import org.pvytykac.ebnf.io.DelimitedReader;
import org.pvytykac.ebnf.tokens.Rule;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 00:33
 */
public class StatefulParserImpl implements StatefulParser, Parser {

    private ParserState state;
    private boolean done = false;
    private List<Rule> rules = new LinkedList<>();

    @Override
    public List<Rule> parse(DelimitedReader reader) {
        while(!done) {
            state.offer(this, reader);
        }

        return rules;
    }

    @Override
    public void writeResult(Rule rule) {
        rules.add(rule);
    }

    @Override
    public void setState(ParserState state) {
        this.state = state;
    }
}
