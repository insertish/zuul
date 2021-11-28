package uk.insrt.coursework.zuul.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.insrt.coursework.zuul.content.campaign.Localisation;

public class LocalisedIO implements IOSystem {
    private final Pattern pattern = Pattern.compile("<([\\w\\.]+?)>");

    private IOSystem io;
    private Localisation locale;

    public LocalisedIO(IOSystem io, Localisation locale) {
        this.io = io;
        this.locale = locale;
    }

    // https://stackoverflow.com/a/27359491
    private String replace(String input) {
        StringBuffer resultString = new StringBuffer();
        Matcher regexMatcher = this.pattern.matcher(input);

        while (regexMatcher.find()) {
            regexMatcher.appendReplacement(resultString, this.locale.get(regexMatcher.group(1)));
        }

        regexMatcher.appendTail(resultString);

        return resultString.toString();
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
}
