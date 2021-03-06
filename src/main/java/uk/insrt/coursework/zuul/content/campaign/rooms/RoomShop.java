package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.entities.EntityShopkeeper;
import uk.insrt.coursework.zuul.world.Direction;
import uk.insrt.coursework.zuul.world.World;

/**
 * A shop within the city, the only one the player can interact with.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class RoomShop extends CampaignRoom {
    public RoomShop(World world) {
        super(world, "Shop");
    }
    
    @Override
    public String describe() {
        return "<shop.enter>";
    }

    @Override
    protected void setupDirections() {
        this.setAdjacent(Direction.SOUTH, this.getWorld().getRoom("Street"));
    }

    @Override
    public void spawnEntities() {
        World world = this.getWorld();
        world.spawnEntity("npc_shopkeeper",
            new EntityShopkeeper(world, this.toLocation()));
    }
}
