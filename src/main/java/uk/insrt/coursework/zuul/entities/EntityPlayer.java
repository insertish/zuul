package uk.insrt.coursework.zuul.entities;

import java.util.ArrayList;

import uk.insrt.coursework.zuul.io.IOSystem;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Player entity which we can control and move around.
 */
public class EntityPlayer extends Entity {
    private ArrayList<Room> previousRooms;
    private ArrayList<Direction> retreatingDirection;

    public EntityPlayer(World world) {
        super(world, new Location(), 70);
        this.previousRooms = new ArrayList<>();
        this.retreatingDirection = new ArrayList<>();
        this.inventory.setMaxWeight(35);
    }

    @Override
    public String[] getAliases() {
        return new String[] {
            "player", "me", "myself", "self", "yourself"
        };
    }

    @Override
    public String describe() {
        // We may skip defining how the Player looks,
        // this is because EntityPlayer is ignored
        // when looking around the room.
        return "";
    }

    /**
     * Move in a direction as instructed by command.
     * @param direction Target Direction
     */
    public void go(Direction direction) {
        var io = this.getWorld().getIO();

        Room room = this.getRoom();
        if (room == null) {
            io.println("You appear to be trapped.");
            return;
        }

        if (!room.canLeave(direction)) return;

        Room destination = room.getAdjacent(direction);
        if (destination == null) {
            io.println("You cannot go this way.");
            return;
        }

        this.retreatingDirection.add(direction.flip());
        this.previousRooms.add(this.getRoom());
        this.setLocation(destination);
    }

    /**
     * Move to the previous room the player was in.
     */
    public void back() {
        IOSystem io = this.getWorld().getIO();
        int index = this.retreatingDirection.size() - 1;

        if (index < 0) {
            io.println("Nowhere to go back to!");
            return;
        }

        Direction lastDirection = this.retreatingDirection.get(index);
        if (this.getRoom().hasExit(lastDirection)) {
            this.retreatingDirection.remove(index);
            this.setLocation(this.previousRooms.remove(index));
        } else {
            io.println("Cannot leave the room this way.");
        }
    }
}
