package org.pvytykac.ebnf.io;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;

/**
 * @author paly
 * @since 21/09/2016 23:30
 */
public final class DelimitedStreamReader extends AbstractDelimiterReader {

    private final InputStreamReader reader;
    private static final char[] BUFF = new char[1];

    public DelimitedStreamReader(InputStreamReader reader) {
        this.reader = reader;
    }

    @Override
    public char next() {
        try {
            int read = reader.read(BUFF, 0, 1);

            //TODO: fix the EOS handling and use the same pattern for string reader (ideally avoid using exception)
            if (read < 0)
                throw new IllegalStateException("end of stream reached");

            return BUFF[0];
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
