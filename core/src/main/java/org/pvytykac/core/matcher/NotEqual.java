package org.pvytykac.core.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author paly
 * @since 18/11/2016 22:40
 */
public class NotEqual extends BaseMatcher<Object> {

    private final Object unexpected;

    public NotEqual(Object unexpected) {
        this.unexpected = unexpected;
    }

    @Override
    public boolean matches(Object item) {
        return !item.equals(unexpected);
    }

    @Override
    public void describeTo(Description description) {}
}
