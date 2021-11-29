package uk.insrt.coursework.zuul.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

/**
 * Window frame for {@link TerminalEmulator}
 */
public class JTerminalFrame extends JFrame {
    private JTerminalView view;
    private boolean fullscreen;

    /**
     * Construct a new JTerminalFrame
     * @param emulator Terminal emulator this frame belongs to
     */
    public JTerminalFrame(TerminalEmulator emulator) {
        this.view = new JTerminalView(emulator);
        this.fullscreen = emulator.isFullscreen();
        this.makeFrame(emulator);
    }

    /**
     * Make and display all of the elements within this frame.
     * @param emulator Terminal emulator this frame belongs to
     */
    public void makeFrame(TerminalEmulator emulator) {
        this.setLayout(new BorderLayout());
        this.add(this.view);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(640, 640));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a listener for any user input.
        // https://stackoverflow.com/a/21970006
        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent event) {
                int code = event.getKeyCode();
                switch (code) {
                    case 8: {
                        emulator.pop();
                        return;
                    }
                    case 10: {
                        emulator.flush();
                        break;
                    }
                    default: {
                        if ((code >= 65 && code <= 90) || (code >= 48 && code <= 57) || code == 32) {
                            emulator.push(event.getKeyChar());
                        }
                    }
                }
            }
    
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });

        // We are ready to display, show everything.
        // Prerequisite to making the frame fullscreen too.
        this.setVisible(true);

        // If we are allowed to launch in fullscreen, switch to that mode now.
        if (this.fullscreen) {
            GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .setFullScreenWindow(this);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        this.view.dispose();
    }
}
