package uk.insrt.coursework.zuul.io;

import java.awt.Color;
import java.util.regex.Pattern;

public class Ansi {
    // used https://stackoverflow.com/a/5762502 as a reference

    public static final String Reset = "\u001B[0m";
    public static final String Black = "\u001B[30m";
    public static final String Red = "\u001B[31m";
    public static final String Green = "\u001B[32m";
    public static final String Yellow = "\u001B[33m";
    public static final String Blue = "\u001B[34m";
    public static final String Purple = "\u001B[35m";
    public static final String Cyan = "\u001B[36m";
    public static final String White = "\u001B[37m";

    public static final String BackgroundBlack = "\u001B[40m";
    public static final String BackgroundRed = "\u001B[41m";
    public static final String BackgroundGreen = "\u001B[42m";
    public static final String BackgroundYellow = "\u001B[43m";
    public static final String BackgroundBlue = "\u001B[44m";
    public static final String BackgroundPurple = "\u001B[45m";
    public static final String BackgroundCyan = "\u001B[46m";
    public static final String BackgroundWhite = "\u001B[47m";

    public static final Pattern AnsiPattern = Pattern.compile("^\\u001B\\[(\\d{1,3})m");

    public static Color fromEscapeCode(int value) {
        switch (value % 10) {
            case 0: return Color.BLACK;
            case 1: return Color.RED;
            case 2: return Color.GREEN;
            case 3: return Color.YELLOW;
            case 4: return Color.BLUE;
            case 5: return Color.MAGENTA;
            case 6: return Color.CYAN;
            case 7:
            default: return Color.WHITE;
        }
    }
}
