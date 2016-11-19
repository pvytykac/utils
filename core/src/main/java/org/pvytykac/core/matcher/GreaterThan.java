package org.pvytykac.core.matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * @author paly
 * @since 18/11/2016 21:43
 */
public class GreaterThan<T extends Comparable> extends BaseMatcher<T> {

    private T expected;

    public GreaterThan(T expected) {
        this.expected = expected;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean matches(Object item) {
        return item instanceof Comparable && ((Comparable) item).compareTo(expected) > 0;
    }

    @Override
    public void describeTo(Description description) {}
}
