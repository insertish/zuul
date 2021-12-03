package uk.insrt.coursework.zuul.events;

/**
 * Interface implementing an listener for an arbitrary {@link Event}.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public interface IEventListener<E extends Event> {
    /**
     * Method called when this specific Event is emitted.
     * @param event Event to handle
     */
    public void onEvent(E event);
}
