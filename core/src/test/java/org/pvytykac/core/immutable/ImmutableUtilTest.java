package org.pvytykac.core.immutable;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.pvytykac.core.immutable.ImmutableUtil.immutableList;
import static org.pvytykac.core.immutable.ImmutableUtil.immutableMap;

/**
 * @author paly
 * @since 23/11/2016 21:52
 */
public class ImmutableUtilTest {

    @Test
    public void testImmutableListNullListNullElement() throws Exception {
        List<Integer> result = immutableList(null, null);
        assertList(result, null, (Integer) null);
    }

    @Test
    public void testImmutableListNullList() throws Exception {
        List<Integer> result = immutableList(null, 1);
        assertList(result, null, 1);
    }

    @Test
    public void testImmutableListNullElement() throws Exception {
        List<Integer> original = Collections.singletonList(1);
        List<Integer> result = immutableList(original, null);

        assertList(result, original, 1, null);
    }

    @Test
    public void testImmutableListEmptyList() throws Exception {
        List<Integer> original = Collections.emptyList();
        List<Integer> result = immutableList(original, 1);

        assertList(result, original, 1);
    }

    @Test
    public void testImmutableList() throws Exception {
        List<Integer> original = Arrays.asList(1, 2, 3);
        List<Integer> result = immutableList(original, 4);

        assertList(result, original, 1, 2, 3, 4);
    }

    @Test
    public void testImmutableMapNullMap() throws Exception {
        Map<String,String> result = immutableMap(null, "key", "value");

        assertMap(result, null, new Entry<>("key", "value"));
    }

    @Test
    public void testImmutableMapEmptyMap() throws Exception {
        Map<String,String> original = Collections.emptyMap();
        Map<String,String> result = immutableMap(original, "key", "value");

        assertMap(result, original, new Entry<>("key", "value"));
    }

    @Test
    public void testImmutableMapNullKey() throws Exception {
        Map<String,String> original = Collections.singletonMap("a", "val_a");
        Map<String,String> result = immutableMap(original, null, "val_b");

        assertMap(result, original, new Entry<>("a", "val_a"), new Entry<>(null, "val_b"));
    }

    @Test
    public void testImmutableMapNullValue() throws Exception {
        Map<String,String> original = Collections.emptyMap();
        Map<String,String> result = immutableMap(original, "key", null);

        assertMap(result, original, new Entry<>("key", null));
    }

    @SafeVarargs
    private static <T> void assertList(List<T> result, List<T> original, T...elements) {
        assertNotNull(result);
        assertNotSame(original, result);
        assertEquals(elements.length, result.size());

        if (original != null)
            assertEquals(elements.length - 1, original.size());

        for (int i = 0; i < elements.length; i++)
            assertEquals(elements[i], result.get(i));

        try {
            result.add(null);
            fail("resulting list should be immutable, add() method should not be allowed");
        } catch (UnsupportedOperationException ignored) {
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SafeVarargs
    private static <K,V> void assertMap(Map<K,V> result, Map<K,V> original, Map.Entry<K,V>...entries) {
        assertNotNull(result);
        assertEquals(entries.length, result.size());

        if (original != null)
            assertEquals(entries.length - 1, original.size());

        for (Map.Entry<K,V> entry: entries)
            assertEquals(entry.getValue(), result.get(entry.getKey()));

        try {
            result.put(null, null);
            fail("resulting map should be immutable, put() method should not be allowed");
        } catch (UnsupportedOperationException ignored){
        } catch (Exception ex) {
            fail("Unexpected exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static class Entry<K,V> implements Map.Entry<K,V> {

        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("setValue() is not implemented");
        }
    }
}