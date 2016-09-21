package org.pvytykac.trf;

import org.junit.Test;
import org.pvytykac.core.Identifiable;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author paly
 * @since 06/08/2016 07:27
 */
public class IdentifiableTransformerTest {

    @Test
    public void testTransform() throws Exception {
        Transformer<From,To> t = new TestTransformer();
        From from = new From(1L, 10);
        To to = t.trf(from);

        assertNotNull(to);
        assertNotNull(to.getId());
        assertNotNull(to.getValue());
        assertEquals(from.getId(), to.getId());
        assertEquals(from.getValue(), to.getValue());
    }

    private static class TestTransformer extends IdentifiableTransformer<Long, From, To> {

        @Override
        protected Class<To> getToClass() {
            return To.class;
        }

        @Override
        protected void transform(From from, To to) {
            to.setValue(from.getValue());
        }
    }

    private static class From implements Identifiable<Long> {
        private Long id;
        private Integer value;

        public From(Long id, Integer value) {
            this.id = id;
            this.value = value;
        }

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }

        public Integer getValue() {
            return value;
        }
    }
    private static class To implements Identifiable<Long> {
        private Long id;
        private Integer value;

        public To() {}

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public void setId(Long id) {
            this.id = id;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

}
