package org.pvytykac.core.tags;

import java.util.LinkedList;
import java.util.List;

/**
 * @author paly
 * @since 16/11/2016 23:46
 */
public class HtmlTag implements Tag {

    private final String name;
    private final List<String> attributes = new LinkedList<>();
    private final List<Tag> body = new LinkedList<>();

    public HtmlTag(String name) {
        this.name = name;
    }

    public HtmlTag id(String id) {
        return attr("id", id);
    }

    public HtmlTag attr(String name) {
        if (name != null)
            attributes.add(name);

        return this;
    }

    public HtmlTag attr(String name, String value) {
        if (name != null && value != null)
            attributes.add(name + "=\"" + value + "\"");

        return this;
    }

    public HtmlTag body(Tag tag) {
        body.add(tag);

        return this;
    }

    public HtmlTag body(String text) {
        if (text != null && !text.isEmpty())
            body.add(new TextTag(text));

        return this;
    }

    @Override
    public String open() {
        if (body.size() > 1)
            throw new IllegalStateException("open() method cannot be used on tags with multiple body elements");

        StringBuilder sb = new StringBuilder();
        sb.append("<")
          .append(name);

        for (String attribute: attributes)
            sb.append(" ")
              .append(attribute);

        sb.append(">");

        for (Tag tag: body)
            sb.append(tag.open());

        return sb.toString();
    }

    @Override
    public String close() {
        if (body.size() > 1)
            throw new IllegalStateException("open() method cannot be used on tags with multiple body elements");

        StringBuilder sb = new StringBuilder();

        for (Tag tag: body)
            sb.append(tag.close());

        sb.append("</")
          .append(name)
          .append(">");

        return sb.toString();
    }

    @Override
    public String full() {
        StringBuilder sb = new StringBuilder();

        sb.append("<")
          .append(name);

        for (String attribute: attributes)
            sb.append(" ")
              .append(attribute);

        if (body.isEmpty()) {
            sb.append("/>");
        } else {
            sb.append(">");

            for (Tag tag: body)
                sb.append(tag.full());

            sb.append("</")
              .append(name)
              .append(">");
        }

        return sb.toString();
    }
}
