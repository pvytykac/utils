package org.pvytykac.jpa;

import org.pvytykac.core.Identifiable;

/**
 * @author paly
 * @since 15.2.2016
 */
public interface Persistent<ID> extends Identifiable<ID> {

    ID getId();
    void setId(ID id);

}
