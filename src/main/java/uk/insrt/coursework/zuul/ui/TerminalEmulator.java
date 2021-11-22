package uk.insrt.coursework.zuul.ui;

import java.awt.EventQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import uk.insrt.coursework.zuul.io.IOSystem;

// https://stackoverflow.com/questions/17922443/drawing-canvas-on-jframe
// https://stackoverflow.com/questions/21969954/how-to-detect-a-key-press-in-java
// https://stackoverflow.com/questions/23413297/passing-values-between-2-threads-without-intrrrupting-each-other
// https://stackoverflow.com/questions/1088595/how-to-do-something-on-swing-component-resizing
// https://fonts.google.com/specimen/VT323?category=Monospace#standard-styles

public class TerminalEmulator implements IOSystem {
    public static final int TERMINAL_WIDTH = 80;
    public static final int TERMINAL_HEIGHT = 25;

    private BlockingQueue<String> queue;
    private JTerminalFrame frame;
    private TextBuffer buffer;
    private String input;

    public TerminalEmulator() {
        this.queue = new LinkedBlockingQueue<>();
        this.buffer = new TextBuffer(TERMINAL_WIDTH, TERMINAL_HEIGHT);
        this.input = new String();

        this.buildFrame();
    }

    public void buildFrame() {
        var emulator = this;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                emulator.frame = new JTerminalFrame(emulator);
            }
        });
    }

    public TextBuffer getBuffer() {
        return this.buffer;
    }

    private void repaint() {
        if (this.frame != null) {
            this.frame.repaint();
        }
    }

    public void dispose() {
        this.frame.dispose();
    }

    public void push(char c) {
        if (this.input.length() == TERMINAL_WIDTH - 4) return;

        this.input += c;
        this.buffer.write(new String(new char[] { c }));
        this.repaint();
    }

    public void pop() {
        if (this.input.length() > 0) {
            this.input = this.input.substring(0, this.input.length() - 1);
            this.buffer.backspace();
            this.repaint();
        }
    }

    public void flush() {
        this.queue.add(this.input);
        this.input = new String();
    }

    @Override
    public void print(String out) {
        buffer.write(out);
        this.repaint();
    }

    @Override
    public void println(String out) {
        buffer.write(out + "\n");
        this.repaint();
    }

    @Override
    public String readLine() {
        try {
            return this.queue.take();
        } catch (Exception err) {
            err.printStackTrace();
            System.exit(1);
            return " ";
        }
    }
}
