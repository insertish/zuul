package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class RoomForest extends CampaignRoom {
    public RoomForest(World world) {
        super(world, "Forest");
    }
    
    public String describe() {
        return "<forest.enter>";
    }

    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.NORTH, world.getRoom("Mainland: Coastline"));
        this.setAdjacent(Direction.EAST, world.getRoom("Worm Hole"));
    }
}
