package org.pvytykac.jpa.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pvytykac.jpa.PersistentMap;
import org.pvytykac.jpa.TestEntity;
import org.pvytykac.jpa.TestEntityJPADAO;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.Arrays;
import static org.junit.Assert.*;

/**
 * @author paly
 * @since 18/02/2016 23:27
 */
public class JPADAOTest {

    private TestEntityJPADAO dao;

    private EntityTransaction transaction;
    private TestEntity[] entities;

    @Before
    public void setUp() throws Exception {
        this.dao = new TestEntityJPADAO();

        // no spring - have to manually init the entity manager from persistence.xml
        if(dao.em == null) {
            dao.em = Persistence.createEntityManagerFactory("TestPU").createEntityManager();
        }

        // start rollback only transaction
        this.transaction = dao.em.getTransaction();
        transaction.begin();
        transaction.setRollbackOnly();

        this.entities = new TestEntity[] {
                new TestEntity("test1"),
                new TestEntity("test2"),
                new TestEntity("test3"),
                new TestEntity("test4"),
                new TestEntity("test5")
        };

        // insert test entities
        for(TestEntity entity: entities) {
            dao.em.persist(entity);
        }
        dao.em.flush();
    }

    @After
    public void tearDown() throws Exception {
        // rollback the transaction
        transaction.rollback();
        dao.em.close();
    }

    // find
    @Test
    public void testFindExists() throws Exception {
        final TestEntity expected = entities[entities.length - 1];

        TestEntity entity = dao.find(expected.getId());

        assertNotNull(entity);
        assertSame(expected, entity);
    }

    @Test
    public void testFindNotExists() throws Exception {
        TestEntity entity = dao.find(entities[0].getId() - 1);

        assertNull(entity);
    }

    // list
    @Test
    public void testListAll() throws Exception {
        PersistentMap<Long, TestEntity> entities = dao.list();

        assertNotNull(entities);
        assertEquals(this.entities.length, entities.size());
        assertContainsAll(entities);
    }

    @Test
    public void testListByVarargIds() throws Exception {
        final TestEntity[] sub = new TestEntity[]{entities[0], entities[1]};
        final Long[] ids = new Long[]{sub[0].getId(), sub[1].getId()};

        PersistentMap<Long, TestEntity> entities = dao.list(ids);

        assertNotNull(entities);
        assertEquals(sub.length, entities.size());
        assertContainsAll(entities, sub);
    }

    @Test
    public void testListByCollectionIds() throws Exception {
        final TestEntity[] sub = new TestEntity[]{entities[2], entities[3]};
        final Long[] ids = new Long[]{sub[0].getId(), sub[1].getId()};

        PersistentMap<Long, TestEntity> entities = dao.list(Arrays.asList(ids));

        assertNotNull(entities);
        assertEquals(sub.length, entities.size());
        assertContainsAll(entities, sub);
    }

    @Test
    public void testListSizeOffset() throws Exception {
        final int size = entities.length;
        final int offset = 0;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);

        assertNotNull(list);
        assertEquals(entities.length, list.size());
        assertContainsAll(list);
    }

    @Test
    public void testListSizeOffsetZero() throws Exception {
        final int size = 0;
        final int offset = 0;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);
        assertNotNull(list);
        assertEquals(entities.length, list.size());
        assertContainsAll(list);
    }

    @Test
    public void testListOffsetGTCount() throws Exception {
        final int size = entities.length;
        final int offset = entities.length;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);

        assertTrue(list.isEmpty());
    }

    @Test
    public void testListSizeGTZero() throws Exception {
        final int size = entities.length - 2;
        final int offset = 0;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);

        assertNotNull(list);
        assertEquals(size, list.size());
        assertContainsAll(list, entities[0], entities[1], entities[2]);
    }

    @Test
    public void testListSizeOffsetGTZero() throws Exception {
        final int size = entities.length - 2;
        final int offset = 2;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);

        assertNotNull(list);
        assertEquals(size, list.size());
        assertContainsAll(list, entities[2], entities[3]);
    }

    @Test
    public void testListSizeGTCount() throws Exception {
        final int size = entities.length + 5;
        final int offset = 0;

        PersistentMap<Long,TestEntity> list = dao.list(size, offset);

        assertNotNull(list);
        assertEquals(entities.length, list.size());
        assertContainsAll(list, entities[2], entities[3]);
    }

    // persist
    @Test
    public void testSaveInsert() throws Exception {
        final TestEntity entity = new TestEntity("inserted");

        Long id = dao.save(entity);

        assertNotNull(id);
        assertEquals(entity.getId(), id);
    }

    @Test
    public void testSaveUpdate() throws Exception {
        final TestEntity entity = entities[0];
        final Long oldId = entity.getId();
        final String oldName = entity.getName();
        final String newName = oldName + " edited";
        entity.setName(newName);

        Long newId = dao.save(entity);

        assertNotNull(newId);
        assertEquals(oldId, newId);
        assertNotSame(oldName, entity.getName());
        assertEquals(newName, entity.getName());
    }

    @Test
    public void testInsertNullId() throws Exception {
        final TestEntity entity = new TestEntity("insert");

        Long id = dao.insert(entity);

        assertNotNull(id);
        assertEquals(entity.getId(), id);
    }

    @Test(expected = PersistenceException.class)
    public void testInsertNonNullIdNotExisting() throws Exception {
        final TestEntity entity = new TestEntity("insert with set id");
        entity.setId(entities[entities.length - 1].getId() + 1);

        dao.insert(entity);
    }

    @Test(expected = PersistenceException.class)
    public void testInsertNonNullIdExisting() throws Exception {
        final TestEntity entity = new TestEntity("insert with existing set id");
        entity.setId(entities[0].getId());

        dao.insert(entity);
    }

    @Test
    public void testUpdateNullId() throws Exception {
        final TestEntity entity = new TestEntity("update without id");
        dao.update(entity);

        // was inserted, but entity object is detached: id = null, count +1
        long count = dao.em.createQuery("SELECT COUNT(e) FROM TestEntity e ", Long.class)
                .getSingleResult();

        assertNull(entity.getId());
        assertEquals(entities.length + 1, count);
    }

    @Test
    public void testUpdateNonNullIdExists() throws Exception {
        final TestEntity entity = entities[0];
        final Long oldId = entity.getId();
        final String oldName = entity.getName();
        final String newName = oldName + " edited";
        entity.setName(newName);

        dao.update(entity);

        assertEquals(oldId, entity.getId());
        assertEquals(newName, entity.getName());
    }

    @Test
    public void testUpdateNonNullIdNotExists() throws Exception {
        final Long id = entities[0].getId() - 1;
        final TestEntity entity = new TestEntity("update with non-existing id");
        entity.setId(id);

        // was inserted, but with autoincrement id
        // entity object is detached
        dao.update(entity);

        assertNotNull(entity.getId());
        assertEquals(id, entity.getId());

        // fetch by set id, check if null
        TestEntity attached = dao.em.find(TestEntity.class, id);
        assertNull(attached);

        // fetch by next autoincrement id, check if matches
        attached = dao.em.find(TestEntity.class, entities[entities.length - 1].getId() + 1);
        assertNotNull(attached);
        assertEquals(entity.getName(), attached.getName());
    }

    // delete
    @Test
    public void testDeleteAll() throws Exception {
        int deleted = dao.deleteAll();

        assertEquals(entities.length, deleted);

        long count = dao.em.createQuery("SELECT COUNT(e) FROM TestEntity e ", Long.class).getSingleResult();
        assertEquals(0, count);
    }

    @Test
    public void testDeleteEntity() throws Exception {
        final TestEntity entity = entities[0];

        dao.delete(entity);

        TestEntity removed = dao.em.find(TestEntity.class, entity.getId());
        assertNull(removed);

        long count = dao.em.createQuery("SELECT COUNT(e) FROM TestEntity e ", Long.class).getSingleResult();
        assertEquals(entities.length - 1, count);
    }

    @Test
    public void testDeleteByVarargIds() throws Exception {
        Long[] ids = new Long[]{entities[0].getId(), entities[1].getId(), entities[4].getId() + 1};
        int deleted = dao.delete(ids);

        assertEquals(ids.length - 1, deleted);
        long count = dao.em.createQuery("SELECT COUNT(e) FROM TestEntity e ", Long.class).getSingleResult();
        assertEquals(entities.length - 2, count);
    }

    @Test
    public void testDeleteByCollectionIds() throws Exception {
        Long[] ids = new Long[]{entities[2].getId(), entities[3].getId(), entities[4].getId(), entities[4].getId() + 1};
        int deleted = dao.delete(ids);

        assertEquals(ids.length - 1, deleted);
        long count = dao.em.createQuery("SELECT COUNT(e) FROM TestEntity e ", Long.class).getSingleResult();
        assertEquals(entities.length - 3, count);
    }

    // count
    @Test
    public void testCountOriginal() throws Exception {
        long count = dao.count();

        assertEquals(entities.length, count);
    }

    @Test
    public void testCountAfterInserts() throws Exception {
        dao.em.persist(new TestEntity("additional1"));
        dao.em.persist(new TestEntity("additional2"));
        dao.em.persist(new TestEntity("additional3"));

        long count = dao.count();

        assertEquals(entities.length + 3, count);
    }

    // custom asserts
    private void assertContainsAll(PersistentMap<Long, TestEntity> list) {
        for(TestEntity entity: this.entities) {
            assertTrue(list.containsEntity(entity));
        }
    }

    private void assertContainsAll(PersistentMap<Long, TestEntity> list, TestEntity...entities) {
        for(TestEntity entity: entities) {
            assertTrue(list.containsEntity(entity));
        }
    }
}
