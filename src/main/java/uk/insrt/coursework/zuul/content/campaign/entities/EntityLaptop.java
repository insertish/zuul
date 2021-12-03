package uk.insrt.coursework.zuul.content.campaign.entities;

import java.awt.Desktop;
import java.net.URI;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.content.campaign.StoryFlags.Stage;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.dialogue.DialogueNode;
import uk.insrt.coursework.zuul.dialogue.DialogueOption;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.Inventory;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * This is the player's laptop which resides in their home.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityLaptop extends EntityWithDialogue<String> implements IUseable {
    /**
     * Construct a new EntityLaptop.
     * @param world World
     * @param location Location
     */
    public EntityLaptop(World world, Location location) {
        super(world, location, 2);
        this.setupDialogue();
    }

    @Override
    public void use(Entity target) {
        this.dialogue.run(this.getWorld().getIO());
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        this.setupDialogueFromId(dialogue, "entity_laptop");

        // Add funny cat videos.
        this.dialogue.addPart("funny_cat_videos",
            new DialogueNode<String>("<entities.laptop.cat_videos.dialog>")
                .addOption(new DialogueOption<String>("<entities.laptop.cat_videos.option_q>",
                    io -> {
                        try {
                            Desktop.getDesktop().browse(new URI("https://youtu.be/k35aiQPgNI4"));
                        } catch (Exception e) { /* If we fail, just ignore this ever happen. */ }

                        return "home";
                    })));

        // Story handler for sending documents to Marie.
        this.dialogue.getPart("document")
            .addOption(new DialogueOption<String>("<entities.laptop.document.option_1>",
                io -> {
                    var w = (CampaignWorld) this.getWorld();
                    var flags = w.getStoryFlags();
                    if (flags.getStage() != Stage.Stealth) {
                        io.println("<marie.comms.no_access>");
                        return "document";
                    }

                    Inventory inv = w.getPlayer().getInventory();
                    int validPieces = 0;
                    for (Entity item : inv.getItems()) {
                        if (item instanceof EntityDocument) {
                            if (((EntityDocument) item).getIsValid()) {
                                validPieces++;
                            }
                        }
                    }
                    
                    if (validPieces == 3) {
                        io.println("<marie.comms.received>");
                        flags.setStage(Stage.End);
                        return null;
                    }

                    io.println("<marie.comms.bad_documents>");
                    return "document";
                }));
    }

    @Override
    public String[] getAliases() {
        return new String[] { "laptop" };
    }

    @Override
    public String describe() {
        return "<entities.laptop.description>";
    }    
}
