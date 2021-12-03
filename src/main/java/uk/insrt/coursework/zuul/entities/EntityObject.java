package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Generic object class which avoids some boilerplate.
 * Use this for entities which are guaranteed to never change.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityObject extends Entity {
    private String description;
    private String[] aliases;

    /**
     * Construct a new EntityObject
     * @param world Current World object
     * @param location Initial Location of this Entity
     * @param weight The weight (in kg) of this Entity
     * @param aliases Aliases which this object can be referred to by
     * @param description A description of this object
     */
    public EntityObject(World world, Location location, double weight, String[] aliases, String description) {
        super(world, location, weight);
        this.description = description;
        this.aliases = aliases;
    }

    /**
     * Construct a new EntityObject
     * @param world Current World object
     * @param location Initial Location of this Entity
     * @param weight The weight (in kg) of this Entity
     * @param name Name of this object
     * @param description A description of this object
     */
    public EntityObject(World world, Location location, double weight, String alias, String description) {
        this(world, location, weight, new String[] { alias }, description);
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
