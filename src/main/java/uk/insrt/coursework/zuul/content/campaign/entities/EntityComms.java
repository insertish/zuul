package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Comms entity which is used to communicate between
 * Marie and the player during the mission.
 */
public class EntityComms extends EntityObject implements IUseable {
    private Dialogue<String> dialogue;
    
    public EntityComms(World world, Location location) {
        super(world, location, 0.3d,
            new String[] { "comms" },
            "<entities.comms>");

        this.dialogue = new Dialogue<>();
        
        ((CampaignWorld) world)
            .getDialogueLoader()
            .populate(this.dialogue, "comms_marie");
    }

    @Override
    public void use(Entity target) {
        var w = (CampaignWorld) this.world;
        var io = w.getIO();

        if (w.getStoryFlags().getStage() != Stage.Stealth) {
            io.println("The device is off.");
            return;
        }

        // start dialogue if necessary
    }
}
