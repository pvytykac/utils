package org.pvytykac.ebnf;

import org.pvytykac.ebnf.io.DelimitedStringReader;
import org.pvytykac.ebnf.parser.StatefulParserImpl;
import org.pvytykac.ebnf.tokens.Rule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

/**
 * @author paly
 * @since 20/09/2016 23:46
 */
public class EBNFParser {

    public static List<Rule> parse(File file) throws IOException {
        return parse(file, Charset.defaultCharset());
    }

    public static List<Rule> parse(File file, Charset charset) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        return parse(new String(bytes, charset));
    }

    public static List<Rule> parse(String string) {
        Parser parser = new StatefulParserImpl();
        return parser.parse(new DelimitedStringReader(string));
    }

}
