package uk.insrt.coursework.zuul.sound;

import uk.insrt.coursework.zuul.events.Event;

/**
 * Event used to tell the sound manager to play a sound.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.1-SNAPSHOT
 */
public class EventSound extends Event {
    private SoundType target;

    /**
     * Construct new EventSound
     * @param target Target sound
     */
    public EventSound(SoundType target) {
        this.target = target;
    }

    /**
     * Get sound type
     * @return Sound type
     */
    public SoundType getTarget() {
        return this.target;
    }
}
