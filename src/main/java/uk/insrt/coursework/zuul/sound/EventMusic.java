package uk.insrt.coursework.zuul.sound;

import uk.insrt.coursework.zuul.events.Event;

/**
 * This event is used to tell the sound manager to start or stop playing music.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.1-SNAPSHOT
 */
public class EventMusic extends Event {
    private MusicType target;
    private boolean play;

    /**
     * Construct new EventMusic
     * @param target Target music type
     * @param play Whether to play or stop
     */
    public EventMusic(MusicType target, boolean play) {
        this.target = target;
        this.play = play;
    }

    /**
     * Get the music type
     * @return music type
     */
    public MusicType getTarget() {
        return this.target;
    }

    /**
     * Get whether it should play
     * @return True if it should play or false if it should stop
     */
    public boolean shouldPlay() {
        return this.play;
    }
}
