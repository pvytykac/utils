package org.pvytykac.jpa;

import java.util.Collection;
import java.util.Set;
/**
 * @author paly
 * @since 15.2.2016
 *
 */
public interface PersistentMap<ID, ENTITY extends Persistent<ID>> extends Iterable<ENTITY> {

    /**
     * @return set of ids of all the entities in this collection
     */
    Set<ID> getIds();

    /**
     * @return collection of values contained in this collection
     */
    Collection<ENTITY> getValues();

    /**
     * @param entity to be added
     */
    void add(ENTITY entity);

    /**
     * @param entity to be added
     * @return null if entity was already added,
     *         currently stored value otherwise
     */
    ENTITY addIfAbsent(ENTITY entity);

    /**
     * @param id of the entity
     * @return entity identified by the specified id
     *         null if entity with the id is not in this collection
     */
    ENTITY get(ID id);

    /**
     * @param entity to check for
     * @return true if specified entity is already in the collection
     *         false otherwise
     */
    boolean containsEntity(ENTITY entity);

    /**
     * @see PersistentMap#addIfAbsent(ENTITY) - if you dont want to overwrite existing entity with the same key
     * @param id to check for
     * @return true if
     */
    boolean containsKey(ID id);

    /**
     * @return number of entities in the map
     */
    int size();

    /**
     * @return true if empty
     *         false otherwise
     */
    boolean isEmpty();
}
