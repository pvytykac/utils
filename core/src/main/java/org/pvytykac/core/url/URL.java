package org.pvytykac.core.url;

import org.apache.commons.lang3.StringUtils;
import org.pvytykac.core.immutable.ImmutableUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * @author paly
 * @since 16/11/2016 23:31
 */
public class URL {

    private final String base;
    private final String anchor;
    private final Map<String, Object> parameters;

    private URL(String base, String anchor, Map<String,Object> parameters) {
        this.base = base;
        this.anchor = anchor;
        this.parameters = parameters;
    }

    /**
     * @return relative URL pointing to the current page
     */
    public static URL url() {
        return url(null);
    }

    /**
     * @param base path to the base resource
     * @return URL pointing to the specified resource
     */
    public static URL url(String base) {
        return new URL(base, null, null);
    }

    /**
     * Adds a '#' anchor to the url
     *
     * @param anchor to be added to the URL
     * @return URL containing the specified anchor
     */
    public URL anchor(String anchor) {
        return ((isEmpty(this.anchor) ^ isEmpty(anchor))
            || (isNotEmpty(this.anchor) && isNotEmpty(anchor) && !StringUtils.equals(anchor, this.anchor)))
            ? new URL(base, anchor, parameters)
            : this;
    }

    /**
     * Adds a path parameter
     *
     * @param key name of the parameter
     * @param value of the parameter, null is converted to empty string by default
     * @return URL containing the specified parameter
     */
    public URL parameter(String key, Object value) {
        return parameter(key, value, true);
    }

    /**
     * Conditionally adds a path parameter
     *
     * @param key name of the parameter, ignored if null
     * @param value of the parameter, null is converted to empty string by default
     * @param condition parameter will be added only if this evaluates to <pre>true</pre>
     * @return URL containing the specified parameter if the condition was met, or original URL otherwise
     */
    public URL parameter(String key, Object value, boolean condition) {
        return (isNotEmpty(key) && value != null && condition)
            ? new URL(base, anchor, ImmutableUtil.immutableMap(parameters, key, value))
            : this;
    }

    public String getBase() {
        return base;
    }

    public String getAnchor() {
        return anchor;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();

        if (base != null)
            buff.append(base);

        if (parameters != null && !parameters.isEmpty()) {
            buff.append('?');

            for (Map.Entry<String, Object> entry : parameters.entrySet())
                buff.append(encode(entry.getKey()))
                    .append("=")
                    .append(encode(entry.getValue().toString()))
                    .append("&");

            buff.deleteCharAt(buff.length() - 1);
        }

        if (anchor != null && !anchor.isEmpty())
            buff.append('#')
                .append(anchor);

        // <a href=""> redirects to current page including current path parameters
        // '?' has to be used to override this to current page + no parameters
        if (buff.length() < 1)
            buff.append('?');

        return buff.toString();
    }

    private String encode(String source) {
        try {
            return URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
