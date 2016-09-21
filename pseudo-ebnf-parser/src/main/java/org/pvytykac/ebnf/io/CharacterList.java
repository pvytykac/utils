package org.pvytykac.ebnf.io;

import java.util.AbstractList;

/**
 * @author paly
 * @since 21/09/2016 23:02
 */
public class CharacterList extends AbstractList<Character> {

    private final char[] chars;

    public CharacterList(char[] chars) {
        this.chars = chars;
    }

    @Override
    public Character get(int index) {
        return chars[index];
    }

    @Override
    public int size() {
        return chars.length;
    }
}
