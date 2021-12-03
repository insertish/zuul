package uk.insrt.coursework.zuul.sound;

import uk.insrt.coursework.zuul.events.Event;

public class EventSound extends Event {
    private SoundType target;

    public EventSound(SoundType target) {
        this.target = target;
    }

    public SoundType getTarget() {
        return this.target;
    }
}
