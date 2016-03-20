package org.pvytykac.jpa;

import java.util.Collection;

/**
 * @author paly
 * @since 15.2.2016
 */
@SuppressWarnings ({"unchecked", "varargs"}) // varargs methods are safe, don't want to use @SafeVarargs because it requires the method to be final
public interface DAO<ENTITY extends Persistent<ID>, ID> {

    /**
     * @param id of the entity
     * @return entity identified by the id or null if entity with the specified id does not exist
     */
    ENTITY find(ID id);

    /**
     * @return {@link PersistentMap} object containing all the entities
     */
    PersistentMap<ID, ENTITY> list();

    /**
     * ids that are not mapped to any entity will not be contained in the returned map
     * @param ids ID vararg containing all the requested ids
     * @return {@link PersistentMap} object containing all the entities identified by specified ids
     */
    PersistentMap<ID, ENTITY> list(ID...ids);

    /**
     * @param ids collection containing all the requested ids
     * @return {@link PersistentMap} object containing all the entities identified by specified ids
     */
    PersistentMap<ID, ENTITY> list(Collection<ID> ids);

    /**
     * @param size max number of entities to return
     * @param offset number of entities to skip
     * @return {@link PersistentMap} object containing all the entities identified by specified ids
     */
    PersistentMap<ID, ENTITY> list(int size, int offset);

    /**
     * Performs insert if id of the entity is null or update otherwise
     * @param entity to be saved
     * @return id of the saved entity
     */
    ID save(ENTITY entity);

    /**
     * Performs update operation
     * @param entity to be updated
     */
    void update(ENTITY entity);

    /**
     * Performs update operation
     * @param entity to be updated
     * @return id of the inserted entity
     */
    ID insert(ENTITY entity);

    /**
     * Deletes the specified entity
     * @param entity to be deleted
     */
    void delete(ENTITY entity);

    /**
     * Deletes the entity identified by the ids
     * @param id vararg containing all the ids of entities to be removed
     * @return number of removed entities
     */
    int delete(ID...id);

    /**
     * Deletes the entity identified by the ids
     * @param ids collection containing all the ids of entities to be removed
     * @return number of removed entities
     */
    int delete(Collection<ID> ids);

    /**
     * Deletes all the entities
     * @return number of removed entities
     */
    int deleteAll();

    /**
     * @return number of all the entities
     */
    Long count();
}
