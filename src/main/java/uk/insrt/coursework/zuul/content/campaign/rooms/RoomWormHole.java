package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomWormHole extends Room {
    public RoomWormHole(World world) {
        super(world, "Worm Hole");
    }
    
    public String describe() {
        return this.getName();
    }

    protected void setupDirections() {}
}
