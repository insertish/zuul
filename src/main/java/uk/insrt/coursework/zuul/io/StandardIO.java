package uk.insrt.coursework.zuul.io;

import java.util.Scanner;

/**
 * A simple IO system implementation which feeds
 * into System.out and takes data from System.in
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class StandardIO implements IOSystem {
    private Scanner reader;

    /**
     * Construct a new StandardIO.
     */
    public StandardIO() {
        this.reader = new Scanner(System.in);
    }

    @Override
    public void print(String out) {
        System.out.print(out);
    }

    @Override
    public void println(String out) {
        System.out.println(out);
    }

    @Override
    public String readLine() {
        return this.reader.nextLine();
    }

    @Override
    public void dispose() {}
}
