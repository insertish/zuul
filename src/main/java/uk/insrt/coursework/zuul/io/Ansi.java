package uk.insrt.coursework.zuul.io;

import java.awt.Color;
import java.util.regex.Pattern;

/**
 * ANSI escape codes
 * Used https://stackoverflow.com/a/5762502 as a reference.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Ansi {
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

    /**
     * Regex Pattern used to match Ansi codes forwards.
     */
    public static final Pattern AnsiPattern = Pattern.compile("^\\u001B\\[(\\d{1,3})m");

    private static final Color ColorBlack = new Color(0, 0, 0);
    private static final Color ColorRed = new Color(224, 108, 117);
    private static final Color ColorGreen = new Color(152, 195, 121);
    private static final Color ColorYellow = new Color(229, 192, 123);
    private static final Color ColorBlue = new Color(97, 175, 239);
    private static final Color ColorMagenta = new Color(198, 120, 221);
    private static final Color ColorCyan = new Color(86, 182, 194);
    private static final Color ColorWhite = new Color(255, 255, 255);

    /**
     * Convert a given escape code value, {@code (\d+?)} in {@link #AnsiPattern}, to a Color.
     * @param value Escape code value
     * @return Resolved Java awt Color
     */
    public static Color fromEscapeCode(int value) {
        switch (value % 10) {
            case 0: return ColorBlack;
            case 1: return ColorRed;
            case 2: return ColorGreen;
            case 3: return ColorYellow;
            case 4: return ColorBlue;
            case 5: return ColorMagenta;
            case 6: return ColorCyan;
            case 7:
            default: return ColorWhite;
        }
    }
}
