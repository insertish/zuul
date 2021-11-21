package uk.insrt.coursework.zuul.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class JTerminalFrame extends JFrame {
    private JTerminalView view;

    public JTerminalFrame(TerminalEmulator emulator) {
        this.view = new JTerminalView(emulator);
        this.makeFrame(emulator);
    }

    public void makeFrame(TerminalEmulator emulator) {
        this.setLayout(new BorderLayout());
        this.add(this.view);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(960, 960));

        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 8: {
                        emulator.pop();
                        return;
                    }
                    case 10: {
                        emulator.flush();
                        break;
                    }
                    default: emulator.push(e.getKeyChar());
                }
            }
    
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
        });

        this.setVisible(true);
    }
}
