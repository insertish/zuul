package uk.insrt.coursework.zuul.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import uk.insrt.coursework.zuul.events.EventSystem;
import uk.insrt.coursework.zuul.io.IOSystem;

/**
 * A terminal emulator which implements an IO system to
 * be arbitrarily plugged into any existing components.
 */
public class TerminalEmulator implements IOSystem {
    public static final int TERMINAL_WIDTH = 80;
    public static final int TERMINAL_HEIGHT = 25;

    private BlockingQueue<String> queue;
    private EventSystem eventSystem;
    private JTerminalFrame frame;
    private boolean fullscreen;
    private TextBuffer buffer;
    private String input;

    /**
     * Construct and build a new TerminalEmulator
     * @param fullscreen Whether to launch the emulator in fullscreen
     */
    public TerminalEmulator(boolean fullscreen) {
        this.queue = new LinkedBlockingQueue<>();
        this.eventSystem = new EventSystem();
        this.buffer = new TextBuffer(TERMINAL_WIDTH, TERMINAL_HEIGHT);
        this.fullscreen = fullscreen;
        this.input = new String();

        this.buildFrame();
    }

    /**
     * Construct a new TerminalEmulator and force windowed mode.
     */
    public TerminalEmulator() {
        this(false);
    }

    /**
     * Build and show the terminal emulator.
     */
    public void buildFrame() {
        var emulator = this;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                emulator.frame = new JTerminalFrame(emulator);
            }
        });
    }

    /**
     * Get the local event system for this emulator.
     * @return The event system
     */
    public EventSystem getEventSystem() {
        return this.eventSystem;
    }

    /**
     * Get the emulator's text buffer.
     * @return The text buffer
     */
    public TextBuffer getBuffer() {
        return this.buffer;
    }

    /**
     * Tell the terminal frame to repaint contents.
     */
    private void repaint() {
        if (this.frame != null) {
            this.frame.repaint();
        }
    }

    /**
     * Check whether we are in fullscreen mode.
     * @return True if we are fullscreen
     */
    public boolean isFullscreen() {
        return this.fullscreen;
    }

    /**
     * Push a new character to the input buffer.
     * @param c Character to push
     */
    public void push(char c) {
        if (this.buffer.getPosX() + 1 == TERMINAL_WIDTH) return;

        this.input += c;
        this.buffer.write(new String(new char[] { c }));
        this.buffer.setLastFg(Color.GRAY);
        this.repaint();
    }

    /**
     * Pop last character from the input buffer.
     */
    public void pop() {
        if (this.input.length() > 0) {
            this.input = this.input.substring(0, this.input.length() - 1);
            this.buffer.backspace();
            this.repaint();
        }
    }

    /**
     * Flush input from terminal emulator thread and send it to whatever is
     * waiting for it on another thread. Uses a blocking queue to send data
     * between threads, as seen here: https://stackoverflow.com/a/23413506
     */
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
            String line = this.queue.take();
            this.print("\n");
            return line;
        } catch (Exception err) {
            err.printStackTrace();
            System.exit(1);
            return " ";
        }
    }

    @Override
    public void dispose() {
        this.frame.dispose();
    }
}
