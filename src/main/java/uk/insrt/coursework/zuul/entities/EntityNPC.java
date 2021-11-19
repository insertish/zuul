package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * NPC entity which provides dialog.
 */
public abstract class EntityNPC extends Entity {
    public EntityNPC(World world, Location startingLocation) {
        super(world, startingLocation, 75);
    }
}
