package uk.insrt.coursework.zuul.content.campaign.rooms;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Class which overrides getWorld to instead provide the CampaignWorld.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public abstract class CampaignRoom extends Room {
    /**
     * Construct a new CampaignRoom.
     * @param world World
     * @param name Internal name used to refer to this Room
     */
    public CampaignRoom(World world, String name) {
        super(world, name);
    }

    @Override
    public CampaignWorld getWorld() {
        return (CampaignWorld) super.getWorld();
    }
}
