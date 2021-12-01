package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Marie Itami
 */
public class EntityMarie extends EntityNpc {
    /**
     * Construct a new EntityMarie.
     * @param world World
     * @param location Location
     */
    public EntityMarie(World world, Location location) {
        super(world, location,
            "npc_marie",
            "<marie.description>",
            new String[] { "marie", "itami", "mink" });
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        super.setupDialogue(dialogue);
        var w = (CampaignWorld) this.world;

        // Progress story if player accepts mission.
        dialogue.getPart("confirm")
            .addOption(new DialogueOption<>("<marie.alley.confirm.option_1>",
                io -> {
                    w.getStoryFlags()
                     .setStage(Stage.Recon);

                    return "recon";
                }));
    }

    @Override
    public void talk() {
        if (this.dialogue.getCurrentNode() == "waiting") {
            //
        }

        super.talk();
    }
}
