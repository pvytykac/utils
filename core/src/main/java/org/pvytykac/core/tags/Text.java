package org.pvytykac.core.tags;

/**
 * @author paly
 * @since 16/11/2016 23:52
 */
class Text implements HtmlElement {

    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public String open() {
        return full();
    }

    @Override
    public String close() {
        return "";
    }

    @Override
    public String full() {
        return text;
    }
}
