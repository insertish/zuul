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
        super(world, new Location(world.getRoom("starting")), 70);
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

    public String[] getAliases() {
        return new String[] {
            "player", "me"
        };
    }

    public boolean pet() {
        return false;
    }

    public boolean take() {
        System.out.println("Where do you want to take yourself to?");
        return false;
    }

    /**
     * Move in a direction as instructed by command.
     * @param direction Target Direction
     */
    public void go(Direction direction) {
        Room room = this.getRoom();
        if (room == null) {
            System.out.println("You appear to be trapped.");
            return;
        }

        String destination = room.getAdjacentRoom(direction);
        if (destination == null) {
            System.out.println("You cannot go this way.");
            return;
        }

        this.retreatingDirection = direction.flip();
        this.setLocation(this.world.getRoom(destination));
    }

    /**
     * Move to the previous room the player was in.
     */
    public void back() {
        if (this.retreatingDirection == null) {
            System.out.println("Nowhere to go back to!");
            return;
        }

        if (this.getRoom().hasExit(this.retreatingDirection)) {
            this.setLocation(this.previousRoom);
            this.retreatingDirection = this.retreatingDirection.flip();
        } else {
            System.out.println("Cannot leave the room this way.");
        }
    }
}
