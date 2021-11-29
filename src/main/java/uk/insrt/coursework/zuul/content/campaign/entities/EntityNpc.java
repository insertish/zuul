package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.entities.actions.ITalkwith;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * NPC entity which provides dialog and can be talked with by the Player.
 */
public class EntityNpc extends EntityWithDialogue<String> implements ITalkwith {
    private String description;
    private String alias[];

    public EntityNpc(World world, Location startingLocation, String id, String description, String alias[]) {
        super(world, startingLocation, 75, null);

        this.description = description;
        this.alias = alias;
        
        this.setupDialogueFromId(dialogue, id);
    }

    public void talk() {
        this.dialogue.run(this.getWorld().getIO());
    }

    @Override
    public String[] getAliases() {
        return this.alias;
    }

    @Override
    public String describe() {
        return this.description;
    }

    @Override
    public void setupDialogue(Dialogue<String> dialogue) {
        // We do not want anything extending EntityNpc to need to
        // setupDialogue themselves as we are already loading it
        // from the dialogue.toml file above.
    }
}