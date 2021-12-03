package uk.insrt.coursework.zuul.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.util.Localisation;

/**
 * Translate and localise any incoming output.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class LocalisedIO implements IOSystem {
    private final Pattern pattern = Pattern.compile("<([\\w\\.]+?)>");

    private IOSystem io;
    private Localisation locale;

    /**
     * Construct a new LocalisedIO.
     * @param io Provided IO system we should feed into
     * @param locale Locale to apply to any i18n strings
     */
    public LocalisedIO(IOSystem io, Localisation locale) {
        this.io = io;
        this.locale = locale;
    }

    /**
     * Replace i18n strings in any given String with their actual localised values.
     * Using replacement code from https://stackoverflow.com/a/27359491.
     * @param input String to process
     * @return Final processed string
     */
    private String replace(String input) {
        StringBuffer result = new StringBuffer();
        Matcher matcher = this.pattern.matcher(input);

        while (matcher.find()) {
            matcher.appendReplacement(result, this.locale.get(matcher.group(1)));
        }

        matcher.appendTail(result);
        return result.toString();
    }

    @Override
    public void print(String out) {
        this.io.print(this.replace(out));
    }

    @Override
    public void println(String out) {
        this.io.println(this.replace(out));
    }

    @Override
    public String readLine() {
        return this.io.readLine();
    }

    @Override
    public void dispose() {
        this.io.dispose();
    }

    @Override
    public void clear() {
        this.io.clear();
    }
}
