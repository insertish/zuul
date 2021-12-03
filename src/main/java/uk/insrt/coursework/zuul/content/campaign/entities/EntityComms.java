package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomMedicalCentreOffice;
import uk.insrt.coursework.zuul.content.campaign.rooms.RoomMedicalCentreReception;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.Room;
import uk.insrt.coursework.zuul.world.World;

/**
 * Comms entity which is used to communicate between
 * Marie and the player during the mission.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityComms extends EntityWithDialogue<String> implements IUseable {
    public EntityComms(World world, Location location) {
        super(world, location, 0.3d);
        this.setupDialogue();
    }

    @Override
    public void use(Entity target) {
        var w = (CampaignWorld) this.world;
        var io = w.getIO();

        if (w.getStoryFlags().getStage() == Stage.Stealth) {
            Room room = w.getPlayer().getRoom();
            if (room instanceof RoomMedicalCentreOffice) {
                this.dialogue.setNodeIfPresent("office");
            } else if (room instanceof RoomMedicalCentreReception) {
                if (((RoomMedicalCentreReception) room).getCouch().isSitting()) {
                    this.dialogue.setNodeIfPresent("in_position");
                } else {
                    this.dialogue.setNodeIfPresent("complaint");
                }
            } else {
                this.dialogue.setNodeIfPresent("orientation");
            }

            this.dialogue.run(io);
        } else {
            io.println("<entities.comms.off>");
        }
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        this.setupDialogueFromId(dialogue, "comms_marie");

        // Make the guards disappear at this point.
        dialogue.getPart("distraction")
            .addOption(new DialogueOption<>(
                "<marie.comms.distraction.option_1>",
                io -> {
                    ((RoomMedicalCentreReception) this.world.getRoom("Medical Centre: Reception"))
                        .getGuard()
                        .consume(false);
                    
                    return "coast_is_clear";
                }
            ));
    }

    @Override
    public String[] getAliases() {
        return new String[] { "comms" };
    }

    @Override
    public String describe() {
        return "<entities.comms.description>";
    }
}
