package uk.insrt.coursework.zuul.io;

import java.util.Scanner;

public class LimitedIO implements IOSystem {
    private final String ansiPattern = "\\u001B\\[(\\d{1,3})m";
    private Scanner reader;

    public LimitedIO() {
        this.reader = new Scanner(System.in);
    }

    @Override
    public void print(String out) {
        System.out.print(out.replaceAll(this.ansiPattern, " "));
    }

    @Override
    public void println(String out) {
        System.out.println(out.replaceAll(this.ansiPattern, " "));
    }

    @Override
    public String readLine() {
        return this.reader.nextLine();
    }

    @Override
    public void dispose() {}
}
