package org.pvytykac.trf;

import org.junit.Test;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * @author paly
 * @since 06/08/2016 06:59
 */
public class AbstractTransformerTest {

    @Test
    public void testTransformReturning() throws Exception {
        Transformer<From, ValidTo> trf = new TestTransformer<>(ValidTo.class);
        String value = "AaBbCcDd";
        From from = new From(value);
        To to = trf.trf(from);

        assertNotNull(to);
        assertEquals(value.toUpperCase(), to.getValue());
    }

    @Test
    public void testTransformModifying() throws Exception {
        Transformer<From, ValidTo> trf = new TestTransformer<>(ValidTo.class);
        String oldValue = "AaBbCcDd";
        String newValue = "AaBbCcDdE";
        From from = new From(newValue);
        ValidTo to = new ValidTo();
        to.setValue(oldValue);

        trf.trf(from, to);

        assertEquals(newValue.toLowerCase(), to.getValue());
    }

    @Test
    public void testTransformEmptyCollection() throws Exception {
        Transformer<From, ValidTo> trf = new TestTransformer<>(ValidTo.class);
        List<ValidTo> liTo = trf.trf((Collection<From>) null);

        assertNotNull(liTo);
        assertTrue(liTo.isEmpty());
    }

    @Test
    public void testTransformCollection() throws Exception {
        Transformer<From, ValidTo> trf = new TestTransformer<>(ValidTo.class);
        int size = 10;
        Set<From> setFrom = new HashSet<>();

        for(int i = 0; i < size; i++)
            setFrom.add(new From(String.format("%05d", i)));

        List<ValidTo> liTo = trf.trf(setFrom);

        assertNotNull(liTo);
        assertFalse(liTo.isEmpty());
        assertTrue(liTo.size() == size);

        for(ValidTo to: liTo) {
            boolean found = false;
            for(From from: setFrom) {
                if(from.getValue().equals(to.getValue())) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testTransformInstantiationException() throws Exception {
        Transformer<From, InstantiationTo> trf = new TestTransformer<>(InstantiationTo.class);
        From from = new From("abcdefgh");

        trf.trf(from);
    }

    @Test(expected = IllegalStateException.class)
    public void testTransformIllegalAccessException() throws Exception {
        Transformer<From, IllegalAccessTo> trf = new TestTransformer<>(IllegalAccessTo.class);
        From from = new From("abcdefgh");

        trf.trf(from);
    }

    private static class TestTransformer<T extends To> extends AbstractTransformer<From, T> {

        private final Class<T> clazz;

        TestTransformer(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public void trf(From from, To to) {
            if(from.getValue().length() % 2 == 0)
                to.setValue(from.getValue().toUpperCase());
            else
                to.setValue(from.getValue().toLowerCase());
        }

        protected Class<T> getToClass() {
            return clazz;
        }
    }

    private static class From {
        private final String value;

        public From(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private interface To {
        String getValue();
        void setValue(String value);
    }
    private static class ValidTo implements To {
        private String value;

        public ValidTo() {}

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    private static class IllegalAccessTo implements To {
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
    private static class InstantiationTo implements To {
        private String value;

        public InstantiationTo(String to) {
            this.value = to;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
