package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomBackAlley extends Room {
    public RoomBackAlley(World world) {
        super(world, "Back Alley");
    }
    
    public String describe() {
        return this.getName();
    }

    protected void setupDirections() {
        this.setAdjacent(Direction.SOUTH, this.getWorld().getRoom("City Centre"));
    }
}
