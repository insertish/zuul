package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.entities.actions.ITalkwith;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * NPC entity which provides dialog.
 */
public abstract class EntityNPC<T> extends EntityWithDialogue<T> implements ITalkwith {
    public EntityNPC(World world, Location startingLocation, T startNode) {
        super(world, startingLocation, 75, startNode);
    }

    public EntityNPC(World world, Location startingLocation) {
        this(world, startingLocation, null);
    }

    public void talk() {
        this.dialogue.run(this.getWorld().getIO());        
    }
}
