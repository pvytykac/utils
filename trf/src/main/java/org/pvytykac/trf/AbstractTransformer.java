package org.pvytykac.trf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author paly
 * @since 06/08/2016 06:43
 */
public abstract class AbstractTransformer<FROM, TO> implements Transformer<FROM, TO> {

    private static final Logger log = LoggerFactory.getLogger(AbstractTransformer.class);

    protected abstract Class<TO> getToClass();

    public final TO trf(FROM from) {
        try {
            TO to = getToClass().newInstance();
            trf(from, to);

            log.trace("Transformed {} to {}", from, to);
            return to;
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("Attempt to transform {} to an object of a class without accessible no-param constructor", from);
            throw new IllegalStateException("The " + getToClass().getName() + " class is required to have a public no-param constructor");
        }
    }

    public final List<TO> trf(Collection<FROM> liFROM) {
        if(liFROM == null || liFROM.isEmpty())
            return new ArrayList<>();

        List<TO> liTO = new ArrayList<>(liFROM.size());
        for(FROM from: liFROM)
            liTO.add(trf(from));

        return liTO;
    }
}
