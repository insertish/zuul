package uk.insrt.coursework.zuul.ui;

import java.awt.Image;

/**
 * Representation of a single Emoji which can be rendered in the terminal emulator.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class Emoji {
    private Image image;
    private int width;
    private int height;

    /**
     * Construct a new Emoji given the image and unicode representation.
     * @param image Image to render when this Emoji is used
     * @param unicode Unicode representation of this Emoji, used to determine width
     */
    public Emoji(Image image, String unicode) {
        this.image = image;
        this.width = (int) unicode.chars().count();
        this.height = 1;
    }

    /**
     * Construct a new Emoji given the image and size constraints.
     * @param image Image to render when this Emoji is used
     * @param width Width of this Emoji
     * @param height Height of this Emoji
     */
    public Emoji(Image image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * Get the Image for this Emoji
     * @return Image
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Get the calculated width of this Emoji
     * @return Width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the calculated height of this Emoji
     * @return Height
     */
    public int getHeight() {
        return this.height;
    }
}
