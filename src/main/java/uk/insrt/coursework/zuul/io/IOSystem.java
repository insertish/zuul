package uk.insrt.coursework.zuul.io;

/**
 * Interface representing an arbitrary IO system.
 * This can be implemented to input or output from various interfaces.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public interface IOSystem {
    /**
     * Print a string out through an arbitrary output channel.
     * @param out String to print
     */
    public void print(String out);

    /**
     * Print a string out through an arbitrary output channel and append {@code \n}.
     * @param out String to print
     */
    public void println(String out);

    /**
     * Read a String up until the first encountered {@code \n} from an arbitrary input channel.
     * @return String of line read in
     */
    public String readLine();

    /**
     * Dispose of the arbitrary input and output channels.
     */
    public void dispose();

    /**
     * Clear the output.
     */
    public void clear();
}
