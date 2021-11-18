package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.events.EventEntityEnteredRoom;
import uk.insrt.coursework.zuul.events.EventEntityLeftRoom;
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

    /**
     * Get the Inventory that this Entity holds.
     * @return Inventory
     */
    public Inventory getInventory() {
        return this.inventory;
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

    /**
     * Move the Entity into a Room.
     * @param room Destination Room
     */
    public void setLocation(Room room) {
        Inventory inventory = this.location.getInventory();
        if (inventory != null) inventory.remove(this);

        Room previousRoom = this.getRoom();
        if (previousRoom != null) this.world.emit(new EventEntityLeftRoom(this, previousRoom));

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
            this.location.setLocation(inventory);
            return true;
        }

        return false;
    }

    /**
     * Take this entity.
     * @return Whether we managed to take this entity.
     */
    public boolean take(Entity target) {
        Inventory inventory = target.getInventory();
        return this.setLocation(inventory);
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

    /**
     * Use this entity.
     * @return Whether this entity can be used.
     */
    public abstract boolean use(Entity target);

    /**
     * Pet this entity.
     * @return Whether this entity can be pet.
     */
    public abstract boolean pet();
}
