package org.pvytykac.jpa;

import org.pvytykac.jpa.impl.JPADAO;

/**
 * @author paly
 * @since 18/02/2016 23:24
 */
public class TestEntityJPADAO extends JPADAO<TestEntity, Long> {

    @Override
    protected Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }

}
