package uk.insrt.coursework.zuul.world;

import java.util.Arrays;
import java.util.List;

/**
 * Enum which represents a Cardinal direction.
 */
public enum Direction {
    NORTH(new String[] { "N" }),
    EAST(new String[] { "E" }),
    SOUTH(new String[] { "S" }),
    WEST(new String[] { "W" });

    private List<String> aliases;

    /**
     * Consturct a new Direction
     * @param aliases Alternative ways to refer to this Direction
     */
    private Direction(String[] aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Check whether this Direction matches the given aliases.
     * @param direction Direction in String format
     * @return Whether it matches.
     */
    private boolean matches(String direction) {
        return this.aliases.contains(direction);
    }

    /**
     * Flip a given Direction in the opposite direction.
     */
    public Direction flip() {
        switch (this) {
            default:
            case NORTH: return Direction.SOUTH;
            case EAST: return Direction.WEST;
            case SOUTH: return Direction.NORTH;
            case WEST: return Direction.EAST;
        }
    }

    /**
     * Convert an arbitrary String to a Direction.
     * @param direction
     * @return
     */
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
