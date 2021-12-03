package uk.insrt.coursework.zuul.events;

/**
 * Represents a single event fired from
 * any source to be consumed by anything.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Event {
    private boolean propagating = true;

    /**
     * Whether this event can continue running.
     * @return Whether propogation of this event was stopped
     */
    public boolean canRun() {
        return this.propagating;
    }

    /**
     * Stop further propagation of this event.
     */
    public void stopPropagation() {
        this.propagating = false;
    }
}
