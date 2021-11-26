package uk.insrt.coursework.zuul.util;

public class BlueJ {
    public static boolean isRunningInBlueJ() {
        return BlueJ.class.getResource("/ThisIsABlueJProject") != null;
    }
}
