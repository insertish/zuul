package uk.insrt.coursework.zuul.ui;

public class TextBuffer {
    private char[][] buffer;
    private int width;
    private int height;

    private int posX;
    private int posY;

    public TextBuffer(int width, int height) {
        this.buffer = new char[height][width];
        this.width = width;
        this.height = height;
        this.posX = 0;
        this.posY = 0;
    }

    public void shift() {
        for (int i=0;i<this.height-1;i++) {
            this.buffer[i] = this.buffer[i + 1];
        }

        this.buffer[this.height - 1] = new char[this.width];
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

    public String getLine(int line) {
        return new String(this.buffer[line]);
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
