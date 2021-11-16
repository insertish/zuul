package uk.insrt.coursework.zuul.world;

import uk.insrt.coursework.zuul.entities.Inventory;

public class Location {
    private Room room;
    private Inventory inventory;

    public Location(Room room) {
        this.room = room;
    }

    public Location(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setLocation(Room room) {
        this.room = room;
        this.inventory = null;
    }

    public void setInventory(Inventory inventory) {
        this.room = null;
        this.inventory = inventory;
    }

    public Room getRoom() {
        return this.room;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
