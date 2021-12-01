package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.content.campaign.CampaignWorld;
import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Abstract implementation of an entity which provides some sort of dialogue.
 */
public abstract class EntityWithDialogue<T> extends Entity {
    protected Dialogue<T> dialogue;

    /**
     * Construct a new EntityWithDialogue with a starting dialogue node
     * @param world Current World object
     * @param location Initial Location of this Entity
     * @param weight The weight (in kg) of this Entity
     * @param startNode The starting dialogue node
     */
    public EntityWithDialogue(World world, Location location, double weight, T startNode) {
        super(world, location, weight);

        Dialogue<T> dialogue = new Dialogue<T>(startNode);
        this.dialogue = dialogue;
    }

    /**
     * Construct a new EntityWithDialogue without a starting dialogue node
     * @param world Current World object
     * @param location Initial Location of this Entity
     * @param weight The weight (in kg) of this Entity
     */
    public EntityWithDialogue(World world, Location location, double weight) {
        this(world, location, weight, null);
    }

    /**
     * Configure this Entity's dialogue,
     * create nodes and options to add to this Entity.
     * @param dialogue Entity Dialogue
     */
    public abstract void setupDialogue(Dialogue<T> dialogue);

    /**
     * Configure dialogue.
     */
    public void setupDialogue() {
        this.setupDialogue(this.dialogue);
    }

    /**
     * Use the CampaignWorld's DialogueLoader to populate this Entity's Dialogue
     * @param dialogue Entity Dialogue
     * @param id Target dialogue ID in file
     */
    public void setupDialogueFromId(Dialogue<String> dialogue, String id) {
        var world = (CampaignWorld) this.getWorld();
        world.getDialogueLoader().populate(dialogue, id);
    }

    /**
     * Set the current dialogue node if the given node is present.
     * @param node Target node
     */
    public void setDialogueNodeIfPresent(Object node) {
        try {
            @SuppressWarnings("unchecked")
            T n = (T) node;

            this.dialogue.setNodeIfPresent(n);
        } catch (ClassCastException ex) {
            // Ignore the error since if we can't cast it
            // to whatever type this is, then obviously this
            // node is not present within this Dialogue.
        }
    }
}
