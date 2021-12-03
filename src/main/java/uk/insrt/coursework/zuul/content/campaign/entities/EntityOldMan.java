package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Quest;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IGiveable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * The old man who is in the forest.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityOldMan extends EntityNpc implements IGiveable {
    /**
     * Construct a new EntityOldMan.
     * @param world World
     * @param location Location
     */
    public EntityOldMan(World world, Location location) {
        super(world, location, "npc_old_man",
            "<forest.old_man.description>",
            new String[] { "oldman", "man" });

        this.inventory.setMaxWeight(EntityCat.WEIGHT);
    }

    @Override
    public void give(Entity item) {
        var io = this.getWorld().getIO();
        if (this.inventory.isFull()) {
            io.println("<forest.old_man.full>");
            return;
        }

        if (item instanceof EntityCat) {
            item.setLocation(this.inventory);
            this.dialogue.setNodeIfPresent("praise");
            ((CampaignWorld) this.getWorld())
                .getStoryFlags()
                .completeSideQuest(Quest.Cat);
            
            io.println("<forest.old_man.accept>");
        } else {
            io.println("<forest.old_man.deny> " + item.getHighlightedName());
        }
    }
}
