package uk.insrt.coursework.zuul.io;

/**
 * Sanitise incoming output and remove any Ansi escape sequences.
 * This is required to print out into the BlueJ console without additional characters.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class SanitiseIO implements IOSystem {
    private final String ansiPattern = "\\u001B\\[(\\d{1,3})m";
    private IOSystem io;

    /**
     * Construct a new SanitiseIO.
     * @param io Provided IO system we should feed into
     */
    public SanitiseIO(IOSystem io) {
        this.io = io;
    }

    @Override
    public void print(String out) {
        this.io.print(out.replaceAll(this.ansiPattern, " "));
    }

    @Override
    public void println(String out) {
        this.io.println(out.replaceAll(this.ansiPattern, " "));
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
