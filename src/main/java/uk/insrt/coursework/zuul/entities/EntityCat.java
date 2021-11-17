package uk.insrt.coursework.zuul.entities;

import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

public class EntityCat extends Entity {
    public EntityCat(World world) {
        super(world, new Location(world.getRoom("starting")), 5);
    }

    public String[] getAliases() {
        return new String[] {
            "cat",
            "the cat"
        };
    }

    public boolean pet() {
        System.out.println("You pet the cat.");
        return true;
    }

    public boolean take() {
        System.out.println("Where do you want to take yourself to?");
        return false;
    }
}
