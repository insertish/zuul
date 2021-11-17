package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * NPC entity which provides dialog.
 */
public class EntityNPC extends Entity {
    public EntityNPC(World world, Location startingLocation) {
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
