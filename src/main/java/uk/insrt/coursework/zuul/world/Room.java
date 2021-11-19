package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Set;

public abstract class Room {
    private World world;
    private String name;
    private HashMap<Direction, Room> adjacentRooms;

    public Room(World world, String name) {
        this.world = world;
        this.name = name;
        this.adjacentRooms = new HashMap<>();
    }

    public World getWorld() {
        return this.world;
    }

    public String getName() {
        return this.name;
    }

    public void setAdjacent(Direction direction, Room room) {
        if (room == null) System.err.println("Warning: assigned null Room to direction " + direction + " for the Room " + this.name);
        this.adjacentRooms.put(direction, room);
    }

    public Room getAdjacent(Direction direction) {
        return this.adjacentRooms.get(direction);
    }

    /**
     * Whether the player can leave in any particular direction.
     * Should print reason if not.
     * @param direction Direction which we are checking
     * @return Whether the player can leave
     */
    public boolean canLeave(Direction direction) {
        return true;
    }

    public Set<Direction> getDirections() {
        return this.adjacentRooms.keySet();
    }

    public boolean hasExit(Direction direction) {
        return this.adjacentRooms.containsKey(direction);
    }

    public void linkRooms() {
        this.adjacentRooms.clear();
        this.setupDirections();
    }
    
    public void spawnEntities() {}

    public Location toLocation() {
        return new Location(this);
    }

    public abstract String describe();
    protected abstract void setupDirections();
}
