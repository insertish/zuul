package uk.insrt.coursework.zuul.world;

import uk.insrt.coursework.zuul.entities.Inventory;

/**
 * Representation of a physical location in the world,
 * whether it is a room or inventory or neither but not both.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Location {
    private Room room;
    private Inventory inventory;

    /**
     * Construct a new Location outside of the World.
     */
    public Location() {}

    /**
     * Construct a new Location pointing to a Room.
     * @param room Room
     */
    public Location(Room room) {
        this.room = room;
    }

    /**
     * Construct a new Location pointing to an Inventory.
     * @param inventory Inventory
     */
    public Location(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Change this Location to point to a Room.
     * @param room Room
     */
    public void setLocation(Room room) {
        this.room = room;
        this.inventory = null;
    }

    /**
     * Change this Location to point to an Inventory.
     * @param inventory Inventory
     */
    public void setLocation(Inventory inventory) {
        this.room = null;
        this.inventory = inventory;
    }

    /**
     * Reset the Location and put us outside of the World.
     */
    public void clear() {
        this.room = null;
        this.inventory = null;
    }

    /**
     * Get the current Room this Location represents.
     * @return Room or null if in an inventory or out of the World.
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Get the current Inventory this Location represents.
     * @return Inventory or null if in a room or out of this World.
     */
    public Inventory getInventory() {
        return this.inventory;
    }
}
