package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.entities.EntityCat;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

public class RoomCityCentre extends Room {
    public RoomCityCentre(World world) {
        super(world, "City Centre");
    }

    public String describe() {
        if (((CampaignWorld) this.getWorld()).hasVisited(this)) {
            return "you've been here before";
        }

        return "something something long description.";
    }

    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.NORTH, world.getRoom("Back Alley"));
        this.setAdjacent(Direction.NORTH_WEST, world.getRoom("Street"));
        this.setAdjacent(Direction.WEST, world.getRoom("Apartments: Reception"));
        this.setAdjacent(Direction.SOUTH, world.getRoom("Coastline"));
    }

    public void spawnEntities() {
        World world = this.getWorld();
        world.spawnEntity("cat", new EntityCat(world, this.toLocation()));
    }
}