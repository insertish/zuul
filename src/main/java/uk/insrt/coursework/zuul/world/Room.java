package uk.insrt.coursework.zuul.world;

import java.util.HashMap;
import java.util.Set;

public abstract class Room {
    protected String name;
    protected HashMap<Direction, Room> adjacentRooms;

    public Room(String name) {
        this.name = name;
        this.adjacentRooms = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public Room getAdjacentRoom(Direction direction) {
        return this.adjacentRooms.get(direction);
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

    public abstract String describe();
    protected abstract void setupDirections();
}
