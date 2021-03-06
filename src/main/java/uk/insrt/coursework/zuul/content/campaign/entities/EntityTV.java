package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * TV entity present in the player's room which they interact
 * with at the start of the game to learn more about the world.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityTV extends EntityWithDialogue<String> implements IUseable {
    public EntityTV(World world, Location location) {
        super(world, location, 40);
        this.setupDialogue();
    }

    @Override
    public void use(Entity target) {
        this.dialogue.run(this.getWorld().getIO());
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        this.setupDialogueFromId(dialogue, "home_tv");
    }

    @Override
    public String[] getAliases() {
        return new String[] { "tv", "television" };
    }

    @Override
    public String describe() {
        return "<home.tv.description>";
    }
}
