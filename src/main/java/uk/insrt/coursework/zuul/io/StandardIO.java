package uk.insrt.coursework.zuul.io;

import java.util.Scanner;

public class StandardIO implements IOSystem {
    private Scanner reader;

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
