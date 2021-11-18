package uk.insrt.coursework.zuul.world;

import java.util.Arrays;
import java.util.List;

/**
 * Enum which represents a Cardinal direction.
 */
public enum Direction {
    NORTH(new String[] { "N" }),
    NORTH_EAST(new String[] { "NE", "NORTH EAST" }),
    EAST(new String[] { "E" }),
    SOUTH_EAST(new String[] { "SE", "SOUTH EAST" }),
    SOUTH(new String[] { "S" }),
    SOUTH_WEST(new String[] { "SW", "SOUTH WEST" }),
    WEST(new String[] { "W" }),
    NORTH_WEST(new String[] { "NW", "NORTH WEST" }),
    
    UP(new String[] {}),
    DOWN(new String[] {});

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
            case NORTH_EAST: return Direction.SOUTH_WEST;
            case EAST: return Direction.WEST;
            case SOUTH_EAST: return Direction.NORTH_WEST;
            case SOUTH: return Direction.NORTH;
            case SOUTH_WEST: return Direction.NORTH_EAST;
            case WEST: return Direction.EAST;
            case NORTH_WEST: return Direction.SOUTH_EAST;
            case UP: return Direction.DOWN;
            case DOWN: return Direction.UP;
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
