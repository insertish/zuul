package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityBed;
import uk.insrt.coursework.zuul.content.campaign.entities.EntityTV;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * The player's home in the apartments complex.
 */
public class RoomApartmentsHome extends CampaignRoom {
    public RoomApartmentsHome(World world) {
        super(world, "Apartments: Home");
    }
    
    @Override
    public String describe() {
        var world = this.getWorld();
        if (!world.hasVisited(this)) {
            return "<home.first_load>";
        }

        return "<home.enter>";
    }

    @Override
    protected void setupDirections() {
        this.setAdjacent(Direction.DOWN, this.getWorld().getRoom("Apartments: Reception"));
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();

        world.spawnEntity("tv", new EntityTV(world, this.toLocation()));
        world.spawnEntity("bed", new EntityBed(world, this.toLocation()));
        world.spawnEntity("laptop", new EntityObject(world, this.toLocation(), 2, new String[] { "laptop" }, "<entities.laptop>"));
    }
}
