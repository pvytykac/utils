package org.pvytykac.core.tags;

/**
 * @author paly
 * @since 16/11/2016 23:52
 */
class TextTag implements Tag {

    private final String text;

    public TextTag(String text) {
        this.text = text;
    }

    @Override
    public String open() {
        return text;
    }

    @Override
    public String close() {
        return "";
    }

    @Override
    public String full() {
        return open();
    }
}
