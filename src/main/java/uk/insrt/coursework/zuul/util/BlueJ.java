package uk.insrt.coursework.zuul.util;

/**
 * Utilities for working with BlueJ
 */
public class BlueJ {
    /**
     * Detect whether this project is likely running in BlueJ
     * by checking whether the marker is present from maven-bluej.
     * @return Whether we are likely running in BlueJ
     */
    public static boolean isRunningInBlueJ() {
        return BlueJ.class.getResource("/ThisIsABlueJProject") != null;
    }
}
