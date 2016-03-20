package org.pvytykac.jpa.impl;

import org.junit.Before;
import org.junit.Test;
import org.pvytykac.jpa.PersistentMap;
import org.pvytykac.jpa.TestEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

/**
 * @author paly
 * @since 19/02/2016 00:30
 */
public class PersistentMapImplTest {

    private PersistentMapImpl<Long, TestEntity> map;

    private static final TestEntity[] ENTITIES = new TestEntity[] {
        new TestEntity(1L, "name1"),
        new TestEntity(2L, "name2"),
        new TestEntity(3L, "name3"),
        new TestEntity(4L, "name4"),
        new TestEntity(5L, "name5")
    };

    @Before
    public void setUp() throws Exception {
        map = new PersistentMapImpl<>(Arrays.asList(ENTITIES));
    }

    @Test
    public void testGetIds() throws Exception {
        Set<Long> ids = map.getIds();

        assertNotNull(ids);
        assertEquals(ENTITIES.length, ids.size());
        for(TestEntity entity: ENTITIES)
            assertTrue(ids.contains(entity.getId()));
    }

    @Test
    public void testGetValues() throws Exception {
        Collection<TestEntity> values = map.getValues();

        assertNotNull(values);
        assertEquals(ENTITIES.length, values.size());
        for(TestEntity entity: ENTITIES)
            assertTrue(values.contains(entity));
    }

    @Test
    public void testAddExisting() throws Exception {
        final TestEntity old = ENTITIES[0];
        final TestEntity entity = new TestEntity(old.getId(), old.getName() + " edited");

        map.add(entity);

        TestEntity stored = map.map.get(old.getId());
        assertNotNull(stored);
        assertEquals(entity.getId(), stored.getId());
        assertEquals(entity.getName(), stored.getName());
    }

    @Test
    public void testAddNew() throws Exception {
        final Long id = getNotExistingId();
        final TestEntity entity = new TestEntity(id, "new entity");

        map.add(entity);

        TestEntity stored = map.map.get(id);
        assertNotNull(stored);
        assertEquals(entity.getId(), stored.getId());
        assertEquals(entity.getName(), stored.getName());
    }

    @Test
    public void testAddIfAbsentExisting() throws Exception {
        final TestEntity old = ENTITIES[0];
        final TestEntity entity = new TestEntity(old.getId(), old.getName() + " edited");

        TestEntity returned = map.addIfAbsent(entity);

        TestEntity stored = map.map.get(old.getId());
        assertNotNull(stored);
        assertNotNull(returned);
        assertSame(stored, returned);
        assertNotSame(stored, entity);
        assertSame(stored, old);
    }

    @Test
    public void testAddIfAbsentNew() throws Exception {
        final Long id = getNotExistingId();
        final TestEntity entity = new TestEntity(id, "new entity");

        TestEntity returned = map.addIfAbsent(entity);

        TestEntity stored = map.map.get(id);
        assertNotNull(stored);
        assertNull(returned);
        assertSame(stored, entity);
        assertEquals(entity.getId(), stored.getId());
        assertEquals(entity.getName(), stored.getName());
    }

    @Test
    public void testGetContains() throws Exception {
        for(TestEntity entity: ENTITIES) {
            TestEntity returned = map.get(entity.getId());
            assertNotNull(returned);
            assertSame(entity, returned);
        }
    }

    @Test
    public void testGetNotContains() throws Exception {
        TestEntity returned = map.get(getNotExistingId());
        assertNull(returned);
    }

    @Test
    public void testContainsEntitySameTrue() throws Exception {
        assertTrue(map.containsEntity(ENTITIES[0]));
    }

    @Test
    public void testContainsEntityNotSameFalse() throws Exception {
        assertFalse(map.containsEntity(new TestEntity(ENTITIES[0].getId(), ENTITIES[0].getName())));
    }

    @Test
    public void testContainsEntityFalse() throws Exception {
        assertFalse(map.containsEntity(new TestEntity(ENTITIES[0].getId(), "same id but different entity")));
    }

    @Test
    public void testContainsKeyTrue() throws Exception {
        assertTrue(map.containsKey(ENTITIES[0].getId()));
    }

    @Test
    public void testContainsKeyFalse() throws Exception {
        assertFalse(map.containsKey(getNotExistingId()));
    }

    @Test
    public void testSizeNonEmpty() throws Exception {
        assertEquals(ENTITIES.length, map.size());
    }

    @Test
    public void testSizeEmpty() throws Exception {
        PersistentMap<Long, TestEntity> emptyMap = new PersistentMapImpl<>(new ArrayList<>());
        assertEquals(0, emptyMap.size());
    }

    @Test
    public void testIsEmptyFalse() throws Exception {
        assertFalse(map.isEmpty());
    }

    @Test
    public void testIsEmptyTrue() throws Exception {
        PersistentMap<Long, TestEntity> emptyMap = new PersistentMapImpl<>(new ArrayList<>());
        assertTrue(emptyMap.isEmpty());
    }

    @Test
    public void testIterator() throws Exception {
        assertNotNull(map.iterator());
    }

    @Test
    public void testSpliterator() throws Exception {
        assertNotNull(map.spliterator());
    }

    @Test
    public void testForEach() throws Exception {
        final Set<Long> ids = new HashSet<>(ENTITIES.length);
        map.forEach(entity -> ids.add(entity.getId()));

        assertEquals(ENTITIES.length, ids.size());
        for(TestEntity entity: ENTITIES)
            assertTrue(ids.contains(entity.getId()));
    }

    private Long getNotExistingId() {
        return ENTITIES[ENTITIES.length - 1].getId() + 1;
    }
}
