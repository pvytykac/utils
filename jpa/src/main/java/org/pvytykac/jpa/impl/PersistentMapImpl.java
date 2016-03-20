package org.pvytykac.jpa.impl;

import org.pvytykac.jpa.Persistent;
import org.pvytykac.jpa.PersistentMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author paly
 * @since 15/02/2016 22:25
 */
public class PersistentMapImpl<ID, ENTITY extends Persistent<ID>> implements PersistentMap<ID, ENTITY> {

    protected Map<ID, ENTITY> map;

    public PersistentMapImpl(List<ENTITY> list) {
        this.map = new HashMap<>();

        for(ENTITY entity: list) {
            map.put(entity.getId(), entity);
        }
    }

    @Override
    public Set<ID> getIds() {
        return map.keySet();
    }

    @Override
    public Collection<ENTITY> getValues() {
        return map.values();
    }

    @Override
    public void add(ENTITY entity) {
        map.put(entity.getId(), entity);
    }

    @Override
    public ENTITY addIfAbsent(ENTITY entity) {
        return map.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public ENTITY get(ID id) {
        return map.get(id);
    }

    @Override
    public boolean containsEntity(ENTITY entity) {
        return map.containsValue(entity);
    }

    @Override
    public boolean containsKey(ID id) {
        return map.containsKey(id);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Iterator<ENTITY> iterator() {
        return map.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super ENTITY> action) {
        map.values().forEach(action);
    }

    @Override
    public Spliterator<ENTITY> spliterator() {
        return map.values().spliterator();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}
