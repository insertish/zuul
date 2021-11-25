package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.events.world.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.world.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.io.Ansi;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Representation of any Entity in the World.
 * 
 * Any living beings, items, or otherwise things that
 * exist in the world are considered an Entity. Each
 * Entity also has an Inventory so things may be stored
 * inside of it.
 */
public abstract class Entity {
    protected World world;
    protected Inventory inventory;
    
    private Location location;
    private int weight;

    /**
     * Construct a new Entity.
     * @param world Current World object
     * @param location Initial Location of this Entity
     * @param weight The weight (in kg) of this Entity
    */
    public Entity(World world, Location location, int weight) {
        this.world = world;
        this.location = location;
        this.inventory = new Inventory();
        this.weight = weight;
    }

    /**
     * Construct a new Entity.
     * 
     * Weight value is set to Integer.MAX_VALUE.
     * @param world Current World object
     * @param location Initial Location of this Entity
    */
    public Entity(World world, Location location) {
        this(world, location, Integer.MAX_VALUE);
    }

    /**
     * Get this Entity's weight.
     * @return Weight (in kg)
     */
    public int getWeight() {
        return this.weight;
    }

    public String getName() {
        return this.getAliases()[0];
    }

    public String getHighlightedName() {
        return Ansi.BackgroundWhite + Ansi.Black + this.getName() + Ansi.Reset;
    }

    /**
     * Get the Inventory that this Entity holds.
     * @return Inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    public World getWorld() {
        return this.world;
    }

    /**
     * Get the Room that this Entity is currently in.
     * @return Room
     */
    public Room getRoom() {
        return this.location.getRoom();
    }

    /**
     * Get the Inventory that this Entity is currently in.
     * @return Inventory
     */
    public Inventory getInventoryWithin() {
        return this.location.getInventory();
    }

    /** Remove from any existing place */
    // * @param suppressEvents Whether to suppress all events.
    public void consume(boolean suppressEvents) {
        Inventory inventory = this.location.getInventory();
        if (inventory != null) inventory.remove(this);

        Room previousRoom = this.getRoom();
        if (previousRoom != null && !suppressEvents) this.world.emit(new EventEntityLeftRoom(this, previousRoom));

        this.location.clear();
    }

    /**
     * Move the Entity into a Room.
     * @param room Destination Room
     */
    public void setLocation(Room room) {
        this.consume(false);
        this.location.setLocation(room);
        this.world.emit(new EventEntityEnteredRoom(this));
    }

    /**
     * Move the Entity into an Inventory.
     * @param inventory Destination Inventory
     * @return Whether we successfully moved the entity into the inventory.
     */
    public boolean setLocation(Inventory inventory) {
        if (inventory.add(this)) {
            this.consume(true);
            this.location.setLocation(inventory);
            return true;
        }

        return false;
    }

    /**
     * Link this Entity's inventory with an existing inventory.
     * @param inventory
     */
    public void entangleInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Get names that this Entity can be called by.
     * @return String array of names for this Entity
     */
    public abstract String[] getAliases();

    /**
     * Get a description of this Entity.
     * @return String describing the Entity
     */
    public abstract String describe();
}
