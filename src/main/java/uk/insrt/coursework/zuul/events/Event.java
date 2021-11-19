package uk.insrt.coursework.zuul.events;

/**
 * Represents a single event fired from
 * any source to be consumed by anything.
 */
public class Event {
    private boolean propagating = true;

    public boolean canRun() {
        return this.propagating;
    }

    public void stopPropagation() {
        this.propagating = false;
    }
}
