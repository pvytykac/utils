package org.pvytykac.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author paly
 * @since 18/02/2016 23:23
 */
@Entity
@Table(name = "test_entity")
public class TestEntity implements Persistent<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_test_entity")
    private Long id;

    @Column(name = "name")
    private String name;

    public TestEntity() {}

    public TestEntity(String name) {
        this.name = name;
    }

    public TestEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
