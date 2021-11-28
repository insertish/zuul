package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityNpc;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

public class RoomApartmentsReception extends CampaignRoom {
    public RoomApartmentsReception(World world) {
        super(world, "Apartments: Reception");
    }
    
    @Override
    public String describe() {
        return "<apartments.enter>";
    }

    @Override
    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.NORTH, world.getRoom("Street"));
        this.setAdjacent(Direction.EAST, world.getRoom("City Centre"));
        this.setAdjacent(Direction.UP, world.getRoom("Apartments: Home"));
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();
        world.spawnEntity("receptionist",
            new EntityNpc(
                world,
                this.toLocation(),
                "npc_receptionist",
                "<apartments.receptionist.description>",
                new String[] { "receptionist" }
            ));
    }
}
