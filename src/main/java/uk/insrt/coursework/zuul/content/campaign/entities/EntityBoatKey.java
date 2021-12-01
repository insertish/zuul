package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Boat key object which is used to unlock and start the
 * speed boat present on the coast.
 */
public class EntityBoatKey extends EntityObject {
    public EntityBoatKey(World world, Location location, int weight, String[] aliases, String description) {
        super(world, location, weight,
            new String[] { "key" },
            "A key to the speed boat");
    }
}
