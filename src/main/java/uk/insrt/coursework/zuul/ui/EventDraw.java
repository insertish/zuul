package uk.insrt.coursework.zuul.ui;

import java.awt.Graphics;

import uk.insrt.coursework.zuul.events.Event;

/**
 * Event fired when the terminal emulator draws a new frame.
 */
public class EventDraw extends Event {
    private Graphics g;
    private float ox;
    private float oy;
    private float fw;
    private float fh;

    /**
     * Construct a new EventDraw Event.
     * @param entity Target Entity
     */
    public EventDraw(Graphics g, float ox, float oy, float fw, float fh) {
        this.g = g;
        this.ox = ox;
        this.oy = oy;
        this.fw = fw;
        this.fh = fh;
    }

    /**
     * Get the Graphics relating to this event.
     * @return Graphics
     */
    public Graphics getGraphics() {
        return this.g;
    }

    /**
     * Get the origin X position of the contents of the terminal.
     * @return X position
     */
    public float getOriginX() {
        return this.ox;
    }

    /**
     * Get the origin Y position of the contents of the terminal.
     * @return Y position
     */
    public float getOriginY() {
        return this.oy;
    }

    /**
     * Get the character width.
     * @return Character width
     */
    public float getCharWidth() {
        return this.fw;
    }

    /**
     * Get the character height.
     * @return Character height
     */
    public float getCharHeight() {
        return this.fh;
    }
}
