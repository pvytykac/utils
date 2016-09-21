package org.pvytykac.ebnf.tokens;

import org.pvytykac.ebnf.Token;

/**
 * @author paly
 * @since 21/09/2016 01:15
 */
public class RegexToken implements Token {

    public final String regex;

    public RegexToken(String regex) {
        this.regex = regex.replaceAll("\\\\", "\\\\")
                          .replaceAll("\"", "\\\"");
    }
}
