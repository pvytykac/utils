package org.pvytykac.core.url;

import org.hamcrest.Matcher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author paly
 * @since 16/11/2016 23:31
 */
public class URL {

    private final String base;
    private final Map<String,Object> parameters = new HashMap<>();
    private String anchor;

    public URL() {
        this(null);
    }

    public URL(String base) {
        this.base = base;
    }

    public URL anchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    public URL parameter(String key, Object value) {
        return parameter(key, value, null);
    }

    public <T> URL parameter(String key, T value, Matcher<T> matcher) {
        if (value != null && (matcher == null || matcher.matches(value)))
            parameters.put(key, value);

        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (base != null)
            sb.append(base);

        if (!parameters.isEmpty())
            sb.append('?');

        for (Map.Entry<String,Object> entry: parameters.entrySet())
            sb.append(encode(entry.getKey()))
                    .append("=")
                    .append(encode(entry.getValue().toString()))
                    .append("&");

        if (!parameters.isEmpty())
            sb.deleteCharAt(sb.length() - 1);

        if (anchor != null && !anchor.isEmpty())
            sb.append('#')
                .append(anchor);

        return sb.toString();
    }

    private String encode(String source) {
        try {
            return URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }
}
