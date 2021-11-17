package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Cat entity which wanders around the map.
 */
public class EntityCat extends Entity {
    public EntityCat(World world, Location startingLocation) {
        super(world, startingLocation, 5);
    }

    public String[] getAliases() {
        return new String[] {
            "cat",
            "the cat"
        };
    }

    public String describe() {
        return "A black cat";
    }

    public boolean pet() {
        System.out.println("You pet the cat.");
        return true;
    }
}
