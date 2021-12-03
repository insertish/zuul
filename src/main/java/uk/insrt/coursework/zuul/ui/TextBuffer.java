package uk.insrt.coursework.zuul.ui;

import java.awt.Color;
import java.util.regex.Matcher;

import uk.insrt.coursework.zuul.io.Ansi;

/**
 * Representation of a text and colour buffer.
 * Provides various utilities for manipulating text on screen.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class TextBuffer {
    private char[][] buffer;
    private Color[][] bufferBg;
    private Color[][] bufferFg;

    private int width;
    private int height;

    private int posX;
    private int posY;

    private Color bg;
    private Color fg;

    private boolean overflow;

    /**
     * Construct a new TextBuffer with given constraints.
     * @param width Buffer width
     * @param height Buffer height
     */
    public TextBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.clear();
    }

    /**
     * Remove top-most row and move all the other rows up.
     */
    public void shift() {
        for (int i=0;i<this.height-1;i++) {
            this.buffer[i] = this.buffer[i + 1];
            this.bufferBg[i] = this.bufferBg[i + 1];
            this.bufferFg[i] = this.bufferFg[i + 1];
        }

        this.bufferBg[this.height - 1] = new Color[this.width];
        this.bufferFg[this.height - 1] = new Color[this.width];
        this.buffer[this.height - 1] = new char[this.width];
    }

    /**
     * Remove the last previous character written to the buffer.
     */
    public void backspace() {
        if (this.posX == 0) return;
        this.buffer[this.posY][--this.posX] = ' ';
    }

    /**
     * Retroactively set the foreground for the previous character written.
     * @param color Foreground colour
     */
    public void setLastFg(Color color) {
        this.bufferFg[this.posY][this.posX - 1] = color;
    }

    /**
     * Retroactively set the background for the previous character written.
     * @param color Background colour
     */
    public void setLastBg(Color color) {
        this.bufferBg[this.posY][this.posX - 1] = color;
    }

    /**
     * Write a new character to the text buffer.
     * This will move the cursor forwards.
     * @param c Character to write
     */
    public void write(char c) {
        // If we encounter a newline, shift downwards or go on to a new line.
        if (c == '\n') {
            if (this.overflow) {
                this.overflow = false;
                return;
            }

            this.posX = 0;

            if (this.posY == this.height - 1) {
                this.shift();
            } else {
                this.posY++;
            }

            return;
        }

        // Clear any overflow value.
        this.overflow = false;

        // Commit new character to buffer.
        this.bufferBg[this.posY][this.posX] = this.bg;
        this.bufferFg[this.posY][this.posX] = this.fg;
        this.buffer[this.posY][this.posX++] = c;

        // If we're at the end of the line, shift downwards or move to new line.
        if (this.posX == this.width) {
            this.posX = 0;

            if (this.posY == this.height - 1) {
                this.shift();
            } else {
                this.posY++;
            }

            // Set a flag to say we just naturally overflowed and to ignore
            // the next newline character that may appear.
            this.overflow = true;
        }
    }

    /**
     * Write a string value to the text buffer.
     * @param value String value to write
     */
    public void write(String value) {
        // Write each character sequentially.
        for (int i=0;i<value.length();i++) {
            char c = value.charAt(i);

            // If we encounter an Ansi escape character, then take the
            // substring from this point on and determine if it is a valid
            // escape code. If it is, apply any changes before continuing.
            if (c == '\u001B') {
                Matcher matcher = Ansi.AnsiPattern.matcher(value.substring(i));
                if (matcher.find()) {
                    int v = Integer.parseInt(matcher.group(1));
                    i += 3 + (v > 9 ? 1 : 0);

                    if (v == 0) {
                        this.bg = Color.BLACK;
                        this.fg = Color.WHITE;
                    } else if (v >= 30 && v < 38) {
                        this.fg = Ansi.fromEscapeCode(v);
                    } else if (v >= 40 && v < 48) {
                        this.bg = Ansi.fromEscapeCode(v);
                    }

                    continue;
                }
            }

            this.write(c);
        }
    }

    /**
     * Get a character at a certain position
     * @param x X position 
     * @param y Y position
     * @return Character at given position
     */
    public char getChar(int x, int y) {
        return this.buffer[y][x];
    }

    /**
     * Get the background colour at a certain position
     * @param x X position 
     * @param y Y position
     * @return Background colour at given position
     */
    public Color getBg(int x, int y) {
        return this.bufferBg[y][x];
    }

    /**
     * Get the foreground colour at a certain position
     * @param x X position 
     * @param y Y position
     * @return Foreground colour at given position
     */
    public Color getFg(int x, int y) {
        return this.bufferFg[y][x];
    }

    /**
     * Get the width of this buffer.
     * @return Width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Get the height of this buffer.
     * @return Height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Get the current X position of the cursor.
     * @return X position of cursor
     */
    public int getPosX() {
        return this.posX;
    }

    /**
     * Get the current Y position of the cursor.
     * @return Y position of cursor
     */
    public int getPosY() {
        return this.posY;
    }

    /**
     * Clear the buffer
     */
    public void clear() {
        this.buffer = new char[height][width];
        this.bufferBg = new Color[height][width];
        this.bufferFg = new Color[height][width];

        this.posX = 0;
        this.posY = 0;

        this.bg = Color.BLACK;
        this.fg = Color.WHITE;

        this.overflow = false;
    }
}
