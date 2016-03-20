package org.pvytykac.jpa.impl;

import org.pvytykac.jpa.DAO;
import org.pvytykac.jpa.Persistent;
import org.pvytykac.jpa.PersistentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author paly
 * @since 15/02/2016 21:57
 */
@SuppressWarnings ({"unchecked", "varargs"}) // varargs methods are safe, don't want to use @SafeVarargs because it requires the method to be final
public abstract class JPADAO<ENTITY extends Persistent<ID>, ID> implements DAO<ENTITY, ID> {

    private static final Logger log = LoggerFactory.getLogger(JPADAO.class);

    @PersistenceContext
    protected EntityManager em;

    @Override
    public ENTITY find(ID id) {
        log.trace("selecting {} with id = {}", getEntityName(), id);
        return em.find(getEntityClass(), id);
    }

    @Override
    public PersistentMap<ID, ENTITY> list() {
        log.trace("listing all {}", getEntityName());
        return list(0, 0);
    }

    @Override
    public PersistentMap<ID, ENTITY> list(ID... ids) {
        log.trace("listing {} with ids in {}", getEntityName(), ids);
        return list(Arrays.asList(ids));
    }

    @Override
    public PersistentMap<ID, ENTITY> list(Collection<ID> ids) {
        log.trace("listing {} with ids in {}", getEntityName(), ids);
        String hql = "SELECT e FROM " + getEntityName() + " e " +
                "WHERE e.id IN :ids ";

        return new PersistentMapImpl<>(em.createQuery(hql, getEntityClass())
                .setParameter("ids", ids)
                .getResultList());
    }

    @Override
    public PersistentMap<ID, ENTITY> list(int size, int offset) {
        log.trace("listing {} offset = {}, size = {}", getEntityName(), offset, size);
        String hql = "SELECT e FROM " + getEntityName() + " e ";
        TypedQuery<ENTITY> query = em.createQuery(hql, getEntityClass());

        if(size > 0)
            query.setMaxResults(size);

        if(offset > 0)
            query.setFirstResult(offset);

        return new PersistentMapImpl<>(query.getResultList());
    }

    @Override
    public ID save(ENTITY entity) {
        log.trace("saving {}: {}", getEntityName(), entity);

        ID id;

        if(entity.getId() != null) {
            update(entity);
            id = entity.getId();
        } else {
            id = insert(entity);
        }

        return id;
    }

    @Override
    public void update(ENTITY entity) {
        em.merge(entity);
    }

    @Override
    public ID insert(ENTITY entity) {
        em.persist(entity);
        return entity.getId();
    }

    @Override
    public int deleteAll() {
        String hql = "DELETE FROM " + getEntityName() + " e ";

        return em.createQuery(hql).executeUpdate();
    }

    @Override
    public void delete(ENTITY entity) {
        em.remove(entity);
    }

    @Override
    public int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    @Override
    public int delete(Collection<ID> ids) {
        String hql = "DELETE FROM " + getEntityName() + " e " +
                "WHERE e.id IN :ids";

        return em.createQuery(hql)
                .setParameter("ids", ids)
                .executeUpdate();
    }

    @Override
    public Long count() {
        String hql = "SELECT COUNT(e) FROM " + getEntityName() + " e ";

        return em.createQuery(hql, Long.class)
                .getSingleResult();
    }

    protected abstract Class<ENTITY> getEntityClass();

    private String getEntityName() {
        return getEntityClass().getSimpleName();
    }
}
