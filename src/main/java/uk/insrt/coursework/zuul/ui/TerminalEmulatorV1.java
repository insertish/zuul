package uk.insrt.coursework.zuul.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uk.insrt.coursework.zuul.io.IOSystem;

// https://stackoverflow.com/questions/17922443/drawing-canvas-on-jframe
// https://stackoverflow.com/questions/21969954/how-to-detect-a-key-press-in-java
// https://stackoverflow.com/questions/23413297/passing-values-between-2-threads-without-intrrrupting-each-other

public class TerminalEmulatorV1 extends JFrame implements IOSystem {
    private TestPane the;
    private String buffer = "";
    BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        new TerminalEmulatorV1();
    }

    public TerminalEmulatorV1() {
        var that = this;
        Thread thread = new Thread("New Thread") {
            public void run(){
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        }

                        JFrame frame = new JFrame("Testing");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLayout(new BorderLayout());
                        var o =new TestPane();
                        o.setBackground(Color.BLACK);
                        o.parent = that;
                        that.the = o;
                        frame.add(o);
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

                        frame.addKeyListener(new KeyListener() {
                            @Override
                            public void keyTyped(KeyEvent e) {
                            }
                    
                            @Override
                            public void keyPressed(KeyEvent e) {
                                if (e.getKeyCode() == 8) {
                                    o.parent.buffer = o.parent.buffer.substring(0, Math.max(0, o.parent.buffer.length() - 1));
                                } else if (e.getKeyCode() == 10) {
                                    o.parent.queue.add(o.o);
                                    o.o = "";
                                    o.parent.buffer += e.getKeyChar();
                                } else {
                                    o.o += e.getKeyChar();
                                    o.parent.buffer += e.getKeyChar();
                                }

                                o.repaint();
                            }
                    
                            @Override
                            public void keyReleased(KeyEvent e) {
                            }
                        });
                    }
                });
            }
        };
        thread.start();
    }

    public class TestPane extends JPanel {
        public TerminalEmulatorV1 parent;
        public String o = "";
        public Font font;
        public int width;

        public TestPane() {
            InputStream stream = this.getClass().getResourceAsStream("/VT323-Regular.ttf");
            try {
                //GraphicsEnvironment ge = 
                    //GraphicsEnvironment.getLocalGraphicsEnvironment();
                font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(32f);
                FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);
                width = (int) font.getStringBounds("a", frc).getWidth();
                System.out.println(font.getStringBounds("a", frc).getWidth() + "x" + font.getStringBounds("a", frc).getHeight());
                //ge.registerFont(font);
            } catch (IOException|FontFormatException e) {
                //Handle exception
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1200, 1200);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // g.setColor(Color.BLACK);
            // g.fillRect(0, 0, WIDTH, HEIGHT);

            //g.setColor(Color.BLACK);
            //g.fillOval(100, 100, 30, 30);

            g.setColor(Color.WHITE);
            g.setFont(this.font);
            int i = 0;

            String[] inLines = this.parent.buffer
                .split("\n");

            List<String> lines = new ArrayList<>();
            for (String in : inLines) {
                while (in.length() > 0) {
                    lines.add(in.substring(0, Math.min(in.length(), 48)));
                    in = in.substring(Math.min(in.length(), 48));
                }
            }

                // 48x25
            
                for (int y=0;y<25;y++) {
                    int pos = lines.size() - 25 + y;
                    if (pos < 0 || pos >= lines.size()) continue;
                    g.drawString(lines.get(pos), (1200 - 48 * this.width) / 2, 200 + 32 * (++i));
                }
                //.forEach(line -> g.drawString(line, 5, 15 + 10 * (++i)));
            
            //g.drawString(this.parent.buffer, 5, 15);
        }
    }

    @Override
    public void print(String out) {
        this.buffer += out;

        if (this.the != null) this.the.repaint();
    }

    @Override
    public void println(String out) {
        this.buffer += out + "\n";

        if (this.the != null) this.the.repaint();
    }

    @Override
    public String readLine() {
        try {
            return this.queue.take();
        } catch (Exception err) {
            err.printStackTrace();
            return " ";
        }
    }
}
