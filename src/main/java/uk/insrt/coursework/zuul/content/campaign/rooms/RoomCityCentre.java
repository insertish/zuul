package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityCat;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityNpc;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * The city centre connecting most major locations.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class RoomCityCentre extends CampaignRoom {
    public RoomCityCentre(World world) {
        super(world, "City Centre");
    }

    @Override
    public String describe() {
        var world = this.getWorld();
        if (!world.hasVisited(this)) {
            return "<city_centre.first_load>";
        }

        return "<city_centre.enter>";
    }

    @Override
    protected void setupDirections() {
        World world = this.getWorld();
        this.setAdjacent(Direction.NORTH, world.getRoom("Back Alley"));
        this.setAdjacent(Direction.NORTH_WEST, world.getRoom("Street"));
        this.setAdjacent(Direction.WEST, world.getRoom("Apartments: Reception"));
        this.setAdjacent(Direction.SOUTH, world.getRoom("Coastline"));
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();

        EntityCat cat = new EntityCat(world, this.toLocation());
        world.spawnEntity("cat", cat);
        cat.useWanderAI(
            new Room[] {
                world.getRoom("City Centre"),
                world.getRoom("Street"),
                world.getRoom("Shop"),
                world.getRoom("Street"),
                world.getRoom("City Centre"),
                world.getRoom("Back Alley"),
                world.getRoom("City Centre")
            },
            8
        );
        
        world.spawnEntity("city_npc",
            new EntityNpc(
                world,
                this.toLocation(),
                "npc_city_centre",
                "<city_centre.npc.description>",
                new String[] { "stranger", "person", "people" }
            ));
    }
}