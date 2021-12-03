package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Set;

/**
 * Representation of a Room within the World.
 * Handles how entities can move from this to other Rooms.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public abstract class Room {
    private World world;
    private String name;
    private HashMap<Direction, Room> adjacentRooms;

    /**
     * Construct a new Room in a given World with a given name.
     * @param world World
     * @param name Internal name used to refer to this Room
     */
    public Room(World world, String name) {
        this.world = world;
        this.name = name;
        this.adjacentRooms = new HashMap<>();
    }

    /**
     * Get the World that this Room is in.
     * @return World
     */
    public World getWorld() {
        return this.world;
    }

    /**
     * Get the internal name of this Room.
     * @return Internal name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Make another Room adjacent to this Room in a particular Direction.
     * @param direction Direction the other Room is in
     * @param room Room which we are making adjacent
     */
    public void setAdjacent(Direction direction, Room room) {
        if (room == null) System.err.println("Warning: assigned null Room to direction " + direction + " for the Room " + this.name);
        this.adjacentRooms.put(direction, room);
    }

    /**
     * Get an adjacent Room in a particular Direction.
     * @param direction Direction to look at
     * @return The Room if one is present in that Direction, otherwise null
     */
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

    /**
     * Get Directions that you can leave this Room in.
     * @return Set of Directions we can leave in
     */
    public Set<Direction> getDirections() {
        return this.adjacentRooms.keySet();
    }

    /**
     * Check whether there is an exit in a particular Direction.
     * @param direction Direction to check
     * @return True if there is an exit in a given Direction
     */
    public boolean hasExit(Direction direction) {
        return this.adjacentRooms.containsKey(direction);
    }

    /**
     * Reset adjacent Rooms and reconfigure adjacent Rooms.
     * This should be called after all Rooms have been spawned into the World.
     */
    public void linkRooms() {
        this.adjacentRooms.clear();
        this.setupDirections();
    }
    
    /**
     * Spawn Entities in this World.
     * By default, nothing is done but this should be used further up to spawn
     * the Entities for this particular Room.
     */
    public void spawnEntities() {}

    /**
     * Convert this Room into a Location.
     * @return Location representation of Room
     */
    public Location toLocation() {
        return new Location(this);
    }

    /**
     * Describe what this Room looks like.
     * @return Description of this Room
     */
    public abstract String describe();

    /**
     * Setup adjacent Rooms.
     */
    protected abstract void setupDirections();
}
