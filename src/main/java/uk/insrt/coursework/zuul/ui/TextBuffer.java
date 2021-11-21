package uk.insrt.coursework.zuul.ui;

import java.awt.Color;

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

    public TextBuffer(int width, int height) {
        this.buffer = new char[height][width];
        this.bufferBg = new Color[height][width];
        this.bufferFg = new Color[height][width];

        this.width = width;
        this.height = height;

        this.posX = 0;
        this.posY = 0;

        this.bg = Color.BLACK;
        this.fg = Color.WHITE;
    }

    public void shift() {
        for (int i=0;i<this.height-1;i++) {
            this.buffer[i] = this.buffer[i + 1];
            this.bufferBg[i] = this.bufferBg[i + 1];
            this.bufferFg[i] = this.bufferFg[i + 1];
        }

        this.buffer[this.height - 1] = new char[this.width];
        this.bufferBg[this.height - 1] = new Color[this.width];
        this.bufferFg[this.height - 1] = new Color[this.width];
    }

    public void backspace() {
        if (this.posX == 0) return;
        this.buffer[this.posY][--this.posX] = ' ';
    }

    public void write(char c) {
        if (c == '\n') {
            this.posX = 0;

            if (this.posY == this.height - 1) {
                this.shift();
            } else {
                this.posY++;
            }
        }

        this.buffer[this.posY][this.posX++] = c;
        this.bufferBg[this.posY][this.posX] = this.bg;
        this.bufferFg[this.posY][this.posX] = this.fg;

        if (this.posX == this.width) {
            this.posX = 0;

            if (this.posY == this.height - 1) {
                this.shift();
            } else {
                this.posY++;
            }
        }
    }

    public void write(String value) {
        for (int i=0;i<value.length();i++) {
            this.write(value.charAt(i));
        }
    }

    public char getChar(int x, int y) {
        return this.buffer[y][x];
    }

    public Color getBg(int x, int y) {
        return this.bufferBg[y][x];
    }

    public Color getFg(int x, int y) {
        return this.bufferFg[y][x];
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }
}
