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
        this.setMinimumSize(new Dimension(1200, 960));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        this.setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.view.dispose();
    }
}