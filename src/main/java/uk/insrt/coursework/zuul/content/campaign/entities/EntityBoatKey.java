package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Boat key object which is used to unlock and start the
 * speed boat present on the coast.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityBoatKey extends EntityObject {
    public EntityBoatKey(World world, Location location) {
        super(world, location, 0.01d,
            new String[] { "key" },
            "<entities.boat_key>");
    }
}
