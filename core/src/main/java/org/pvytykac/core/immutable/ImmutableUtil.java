package org.pvytykac.core.immutable;

import java.util.*;

/**
 * @author paly
 * @since 23/11/2016 21:51
 */
public class ImmutableUtil {

    public static <T> List<T> immutableList(List<T> list, T element) {
        List<T> copy = (list == null)
            ? new LinkedList<>()
            : new LinkedList<>(list);

        copy.add(element);
        return Collections.unmodifiableList(copy);
    }

    public static <K,V> Map<K,V> immutableMap(Map<K,V> map, K key, V value) {
        Map<K,V> copy = (map == null)
            ? new LinkedHashMap<>()
            : new LinkedHashMap<>(map);

        copy.put(key, value);
        return Collections.unmodifiableMap(copy);
    }

}
