package uk.insrt.coursework.zuul.world;

public abstract class Room {
    private String name;

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract String getAdjacentRoom(Direction dir);

    public boolean hasExit(Direction dir) {
        return this.getAdjacentRoom(dir) != null;
    }
}
