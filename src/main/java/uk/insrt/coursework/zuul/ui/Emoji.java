package uk.insrt.coursework.zuul.ui;

import java.awt.Image;

public class Emoji {
    private Image image;
    private int width;
    private int height;

    public Emoji(Image image, String unicode) {
        this.image = image;
        this.width = (int) unicode.chars().count();
        this.height = 1;
    }

    public Emoji(Image image, int width, int height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    public Image getImage() {
        return this.image;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
