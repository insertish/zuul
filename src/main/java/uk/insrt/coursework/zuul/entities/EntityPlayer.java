package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Player entity which we can control and move around.
 */
public class EntityPlayer extends Entity {
    private Room previousRoom;
    private Direction retreatingDirection;

    public EntityPlayer(World world) {
        super(world, new Location(), 70);
        this.inventory.setMaxWeight(35);
    }

    /**
     * Override method for setLocation which
     * keeps track of previous room.
     */
    @Override
    public void setLocation(Room room) {
        this.previousRoom = this.getRoom();
        super.setLocation(room);
    }

    @Override
    public String[] getAliases() {
        return new String[] {
            "player", "me"
        };
    }

    @Override
    public String describe() {
        // We may skip defining how the Player looks,
        // this is because EntityPlayer is ignored
        // when looking around the room.
        return "";
    }

    @Override
    public boolean take(Entity target) {
        return false;
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

        this.retreatingDirection = direction.flip();
        this.setLocation(destination);
    }

    /**
     * Move to the previous room the player was in.
     */
    public void back() {
        var io = this.getWorld().getIO();
        
        if (this.retreatingDirection == null) {
            io.println("Nowhere to go back to!");
            return;
        }

        if (this.getRoom().hasExit(this.retreatingDirection)) {
            this.setLocation(this.previousRoom);
            this.retreatingDirection = this.retreatingDirection.flip();
        } else {
            io.println("Cannot leave the room this way.");
        }
    }
}
