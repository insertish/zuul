package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Generic object class which avoids some boilerplate.
 * Use this for entities which are guaranteed to never change.
 */
public class EntityObject extends Entity {
    private String description;
    private String[] aliases;

    public EntityObject(World world, Location location, int weight, String[] aliases, String description) {
        super(world, location, weight);
        this.description = description;
        this.aliases = aliases;
    }
    
    @Override
    public String describe() {
        return this.description;
    }

    @Override
    public String[] getAliases() {
        return this.aliases;
    }
}
