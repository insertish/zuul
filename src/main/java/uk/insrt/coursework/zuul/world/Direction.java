package uk.insrt.coursework.zuul.world;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(new String[] { "N" }),
    EAST(new String[] { "E" }),
    SOUTH(new String[] { "S" }),
    WEST(new String[] { "W" });

    private List<String> aliases;
    private Direction(String[] aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    private boolean matches(String direction) {
        return this.aliases.contains(direction);
    }

    public static Direction fromString(String direction) {
        if (direction == null) return null;

        String directionFormatted = direction.toUpperCase();
        try {
            return Direction.valueOf(directionFormatted);
        } catch (Exception ex) {
            for (Direction dir : Direction.values()) {
                if (dir.matches(directionFormatted)) {
                    return dir;
                }
            }
    
            return null;
        }
    }
}
