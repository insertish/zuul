package uk.insrt.coursework.zuul.events;

import uk.insrt.coursework.zuul.entities.Entity;
import uk.insrt.coursework.zuul.entities.EntityPlayer;

/**
 * Event system which manages taking in events
 * from different sources and handles them
 * appropriately by looking up any event listeners
 * and firing their callbacks.
 */
public class EventSystem {
    public void emit(Event event) {
        // temp
        if (event instanceof EventEntityEnteredRoom) {
            Entity entity = ((EventEntityEnteredRoom) event).getEntity();
            if (entity instanceof EntityPlayer) {
                System.out.println("Player is now in " + entity.getRoom().getName());
            }
        }
    }
}
