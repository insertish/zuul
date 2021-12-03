package uk.insrt.coursework.zuul.content.campaign.entities;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityObject;
import uk.insrt.coursework.zuul.entities.actions.IUseable;
import uk.insrt.coursework.zuul.events.IEventListener;
import uk.insrt.coursework.zuul.events.world.EventEntityLeftRoom;
import uk.insrt.coursework.zuul.world.Location;
import uk.insrt.coursework.zuul.world.World;

/**
 * Couch present in the Medical Centre reception area.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EntityCouch extends EntityObject implements IUseable, IEventListener<EventEntityLeftRoom> {
    private boolean isSitting;

    /**
     * Construct a new EntityCouch.
     * @param world World 
     * @param location Location
     */
    public EntityCouch(World world, Location location) {
        super(world, location, Double.MAX_VALUE, "couch", "<entities.couch.description>");
    }

    @Override
    public void use(Entity target) {
        var io = this.getWorld().getIO();
        if (this.isSitting) {
            io.println("<entities.couch.sitting>");
        } else {
            io.println("<entities.couch.sit>");
            this.isSitting = true;
        }
    }

    @Override
    public void onEvent(EventEntityLeftRoom event) {
        this.isSitting = false;
    }

    /**
     * Whether the player is sitting on the couch.
     * @return True if the player is sat down
     */
    public boolean isSitting() {
        return this.isSitting;
    }
}
