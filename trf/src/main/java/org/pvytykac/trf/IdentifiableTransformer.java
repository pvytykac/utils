package org.pvytykac.trf;

import org.pvytykac.core.Identifiable;

/**
 * @author paly
 * @since 06/08/2016 06:55
 */
public abstract class IdentifiableTransformer<ID, FROM extends Identifiable<ID>, TO extends Identifiable<ID>>
        extends AbstractTransformer<FROM, TO> {

    protected abstract void transform(FROM from, TO to);

    @Override
    public void trf(FROM from, TO to) {
        to.setId(from.getId());
        transform(from, to);
    }

}
