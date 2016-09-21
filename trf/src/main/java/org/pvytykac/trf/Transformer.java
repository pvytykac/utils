package org.pvytykac.trf;

import java.util.Collection;
import java.util.List;

/**
 * @author paly
 * @since 6. 8. 2016
 */
public interface Transformer<FROM, TO> {

    TO trf(FROM from);

    void trf(FROM from, TO to);

    List<TO> trf(Collection<FROM> from);

}
