package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public abstract class Entity {
    protected World world;
    protected Inventory inventory;
    
    private Location location;
    private int weight;

    public Entity(World world, Location location, int weight) {
        this.world = world;
        this.location = location;
        this.inventory = new Inventory();
        this.weight = weight;
    }

    public Entity(World world, Location location) {
        this(world, location, Integer.MAX_VALUE);
    }

    public int getWeight() {
        return this.weight;
    }

    protected Room getRoom() {
        return this.location.getRoom();
    }

    public void setLocation(Room room) {
        Inventory inventory = this.location.getInventory();
        if (inventory != null) inventory.remove(this);

        this.location.setLocation(room);
    }

    public void setLocation(Inventory inventory) {
        this.location.setLocation(inventory);
        inventory.add(this);
    }

    public boolean isInRoom(Room room) {
        return this.location.getRoom() == room;
    }

    public abstract boolean pet();
    public abstract boolean take();
}
