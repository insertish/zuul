package uk.insrt.coursework.zuul.content.campaign.entities.npc;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.entities.EntityNPC;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public class EntityNpcReceptionist extends EntityNPC<String> {
    public EntityNpcReceptionist(World world, Location startingLocation) {
        super(world, startingLocation);
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        var world = (CampaignWorld) this.getWorld();
        world.getDialogueLoader().populate(dialogue, "npc_receptionist");
    }

    @Override
    public String[] getAliases() {
        return new String[] { "receptionist" };
    }

    @Override
    public String describe() {
        return "The receptionist.";
    }
}
