package uk.insrt.coursework.zuul.world;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public static Direction fromString(String direction) {
        if (direction == null) return null;

        try {
            return Direction.valueOf(direction.toUpperCase());
        } catch (Exception ex) {
            switch (direction) {
                case "N": return Direction.NORTH;
                case "E": return Direction.EAST;
                case "S": return Direction.SOUTH;
                case "W": return Direction.WEST;
            }
    
            return null;
        }
    }
}
