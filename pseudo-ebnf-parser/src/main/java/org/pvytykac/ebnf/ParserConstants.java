package org.pvytykac.ebnf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author paly
 * @since 21/09/2016 01:02
 */
public class ParserConstants {

    public static final char RULE_ASSIGNMENT_DELIMITER = '=';
    public static final char SUBSTITUTION_DELIMITER = '|';
    public static final char RULE_DELIMITER = ';';
    public static final char CONCAT_DELIMITER = ',';
    public static final char SPECIAL_DELIMITER = '?';
    public static final char SPECIAL_GROUP_DELIMITER = ':';

    public static final char ESCAPE_CHAR = '\\';

    public static final char SPECIAL_GROUP_REGEX = 'R';

    public static final List<Character> TERM_DELIMITER = new ArrayList<>();

    static {
        TERM_DELIMITER.add('\'');
        TERM_DELIMITER.add('\"');
    }

}
