package org.pvytykac.jpa;

/**
 * @author paly
 * @since 15.2.2016
 */
public interface Persistent<ID> {

    ID getId();
    void setId(ID id);

}
