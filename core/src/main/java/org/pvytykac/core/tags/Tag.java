package org.pvytykac.core.tags;

import org.apache.commons.lang3.StringUtils;
import org.pvytykac.core.immutable.ImmutableUtil;

import java.util.List;

/**
 * @author paly
 * @since 16/11/2016 23:46
 */
public class Tag implements HtmlElement {

    private final String name;
    private final List<String> attributes;
    private final List<HtmlElement> body;

    public static Tag tag(String name) {
        return new Tag(name, null, null);
    }

    private Tag(String name, List<String> attributes, List<HtmlElement> body) {
        if (StringUtils.isEmpty(name))
            throw new IllegalArgumentException("name cannot be empty nor null");

        this.name = name;
        this.attributes = attributes;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    /**
     * Adds an attribute without a value to the element
     * ie. required attribute of input element
     *
     * @param attribute name of the attribute, ignored if null or empty
     * @return tag containing the attribute
     */
    public Tag attr(String attribute) {
        return attr(attribute, true);
    }

    /**
     * Adds an attribute without a value to the element if the condition is met
     * ie. required attribute of input element
     *
     * @param attribute name of the attribute, ignored if null or empty
     * @param condition attribute is added only if the value is true
     * @return tag containing the attribute
     */
    public Tag attr(String attribute, boolean condition) {
        return (!StringUtils.isEmpty(attribute) && condition)
            ? new Tag(name, ImmutableUtil.immutableList(attributes, attribute), body)
            : this;
    }


    /**
     * Adds an attribute with a value to the element
     *
     * @param attribute name of the attribute to be added, ignored if null or empty
     * @param value value of the attribute, converted to empty string if null
     * @return tag containing the attribute
     */
    public Tag attr(String attribute, String value) {
        return attr(attribute, value, true);
    }

    /**
     * Adds an attribute with a value to the element if the condition is met
     *
     * @param attribute name of the attribute to be added, ignored if null or empty
     * @param value value of the attribute, converted to empty string if null
     * @param condition attribute is added only if the value is true
     * @return tag containing the attribute
     */
    public Tag attr(String attribute, String value, boolean condition) {
        if(value == null)
            value = "";

        return (!StringUtils.isEmpty(attribute) && condition)
            ? new Tag(name, ImmutableUtil.immutableList(attributes, attribute + "=\"" + value + "\""), body)
            : this;
    }

    /**
     * Adds a child html element
     *
     * @param child html element to be wrapped by the current one, ignored if null
     * @return tag containing the child element
     */
    public Tag body(HtmlElement child) {
        return (child != null)
            ? new Tag(name, attributes, ImmutableUtil.immutableList(body, child))
            : this;
    }

    /**
     * Adds a text to the current html element
     *
     * @param text to be added as a child to the current html element
     * @return tag containing the child text
     */
    public Tag body(String text) {
        return (!StringUtils.isEmpty(text))
            ? new Tag(name, attributes, ImmutableUtil.immutableList(body, new Text(text)))
            : this;
    }

    @Override
    public String open() {
        int bodySize = getBodySize();
        if (bodySize > 1)
            throw new IllegalStateException("open() method cannot be used on tags with multiple body elements");

        StringBuilder buff = new StringBuilder();

        writeOpenTag(buff, false);

        if (bodySize > 0)
            for (HtmlElement tag: body)
                buff.append(tag.open());

        return buff.toString();
    }

    @Override
    public String close() {
        int bodySize = getBodySize();
        if (bodySize > 1)
            throw new IllegalStateException("open() method cannot be used on tags with multiple body elements");

        StringBuilder buff = new StringBuilder();

        if (bodySize > 0)
            for (HtmlElement tag: body)
                buff.append(tag.close());

        writeCloseTag(buff);

        return buff.toString();
    }

    @Override
    public String full() {
        int bodySize = getBodySize();
        StringBuilder buff = new StringBuilder();

        writeOpenTag(buff, bodySize < 1);

        if (bodySize > 0) {
            for (HtmlElement el : body)
                buff.append(el.full());

            writeCloseTag(buff);
        }

        return buff.toString();
    }

    private void writeOpenTag(StringBuilder buff, boolean selfclosed) {
        buff.append("<")
            .append(name);

        if (attributes != null)
            for (String attribute: attributes)
                buff.append(" ")
                    .append(attribute);

        if (selfclosed)
            buff.append("/>");
        else
            buff.append(">");
    }

    private void writeCloseTag(StringBuilder buff) {
        buff.append("</")
            .append(name)
            .append(">");
    }

    private int getBodySize() {
        return (body == null)
            ? 0
            : body.size();
    }

    @Override
    public String toString() {
        return full();
    }
}

