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
    public EntityWithDialogue(World world, Location location, int weight, T startNode) {
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
    public EntityWithDialogue(World world, Location location, int weight) {
        this(world, location, weight, null);
    }

    /**
     * Configure this Entity's dialogue,
     * create nodes and options to add to this Entity.
     * @param dialogue Entity Dialogue
     */
    public abstract void setupDialogue(Dialogue<T> dialogue);

    /**
     * Use the CampaignWorld's DialogueLoader to populate this Entity's Dialogue
     * @param dialogue Entity Dialogue
     * @param id Target dialogue ID in file
     */
    public void setupDialogueFromId(Dialogue<String> dialogue, String id) {
        var world = (CampaignWorld) this.getWorld();
        world.getDialogueLoader().populate(dialogue, id);
    }
}
