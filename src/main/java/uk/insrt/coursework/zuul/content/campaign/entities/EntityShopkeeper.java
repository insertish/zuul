package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueNode;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public class EntityShopkeeper extends EntityNpc {
    public EntityShopkeeper(World world, Location startingLocation) {
        super(world, startingLocation,
            "npc_shopkeeper",
            "<shop.npc.description>",
            new String[] { "shopkeeper" });
    }

    private class IndexNode extends DialogueNode<String> {
        public IndexNode() {
            super(null);
        }

        @Override
        public String getDescription() {
            var w = (CampaignWorld) world;
            return "<shop.npc.greeting."
                + w.getStoryFlags().getStage().toString() + ">";
        }
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        dialogue.addPart("index", new IndexNode());
        dialogue.setNodeIfPresent("index");
        
        /*super.setupDialogue(dialogue);

        var index = dialogue.getPart("index");
        var recon = dialogue.getPart("recon");
        var stelf = dialogue.getPart("stealth");

        var itemBoatKey = new DialogueOption<String>(
            "<shop.npc.item_boat>",
            io -> {
                String menu = dialogue.getCurrentNode();
                io.println("check stock");

                return menu;
            }
        );

        index.addOption(itemBoatKey);
        recon.addOption(itemBoatKey);
        stelf.addOption(itemBoatKey);*/
    }
}
