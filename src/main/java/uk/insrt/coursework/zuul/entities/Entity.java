package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class Entity {
    private World world;
    private String room;

    public Entity(World world, String room) {
        this.world = world;
        this.room = room;
    }

    public boolean pet() {
        return false;
    }

    public boolean go(Direction dir) {
        Room room = world.getRoom(this.room);
        String destination = room.getAdjacentRoom(dir);
        if (destination == null) {
            return true;
        }

        this.room = destination;
        System.out.println("Entity is now in " + destination);

        return false;
    }
}
