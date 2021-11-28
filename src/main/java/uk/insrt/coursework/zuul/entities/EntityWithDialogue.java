package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.dialogue.Dialogue;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public abstract class EntityWithDialogue<T> extends Entity {
    protected Dialogue<T> dialogue;

    public EntityWithDialogue(World world, Location location, int weight, T startNode) {
        super(world, location, weight);

        Dialogue<T> dialogue = new Dialogue<T>(startNode);
        this.setupDialogue(dialogue);
        this.dialogue = dialogue;
    }

    public EntityWithDialogue(World world, Location location, int weight) {
        this(world, location, weight, null);
    }

    public abstract void setupDialogue(Dialogue<T> dialogue);
}
