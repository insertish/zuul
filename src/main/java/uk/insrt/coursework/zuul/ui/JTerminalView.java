package uk.insrt.coursework.zuul.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.InputStream;

import javax.swing.JPanel;

public class JTerminalView extends JPanel {
    private TerminalEmulator emulator;
    private boolean blinkState;
    private Thread thread;
    private int fw, fh;
    private Font font;

    public JTerminalView(TerminalEmulator emulator) {
        this.emulator = emulator;
        this.blinkState = false;
        this.makeFrame();
    }

    public void makeFrame() {
        this.setBackground(Color.BLACK);
        this.loadFont("/VT323-Regular.ttf");

        var view = this;
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                view.repaint();
            }
        });

        this.thread = new Thread("Blink Thread") {
            public void run() {
                try {
                    while (true) {
                        view.blinkState = !view.blinkState;
                        view.repaint();

                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {}
            }
        };

        this.thread.start();
    }

    public void dispose() {
        // https://docs.oracle.com/javase/1.5.0/docs/guide/misc/threadPrimitiveDeprecation.html
        this.thread.interrupt();
    }

    public void loadFont(String source) {
        InputStream stream = this.getClass().getResourceAsStream(source);

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, stream)
                .deriveFont(24f);
            
            var frc = new FontRenderContext(new AffineTransform(), true, true);
            var bounds = this.font.getStringBounds(" ", frc);
            this.fw = (int) bounds.getWidth();
            this.fh = (int) bounds.getHeight();
        } catch (Exception e) { }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1280, 960);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setFont(this.font);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        TextBuffer buffer = this.emulator.getBuffer();
        int ox = (this.getWidth() - this.fw * buffer.getWidth()) / 2;
        int oy = (this.getHeight() - this.fh * buffer.getHeight()) / 2;

        for (int y=0;y<buffer.getHeight();y++) {
            for (int x=0;x<buffer.getWidth();x++) {
                Color bg = buffer.getBg(x, y);
                Color fg = buffer.getFg(x, y);

                int drawX = ox + this.fw * x;
                int drawY = oy + this.fh + this.fh * y;

                if (bg != null && bg != Color.BLACK) {
                    g.setColor(bg);
                    g.fillRect(drawX, drawY - this.fh + 6, this.fw, this.fh);
                }

                char c = buffer.getChar(x, y);
                if (c != ' ') {
                    g.setColor(fg);
                    g.drawString(
                        String.valueOf(c),
                        drawX,
                        drawY
                    );
                }
            }
        }

        if (this.blinkState) {
            g.setColor(Color.WHITE);
            g.fillRect(
                ox + this.fw * buffer.getPosX() + 1,
                oy + this.fh * (buffer.getPosY() + 1) + 2,
                this.fw - 2,
                (int) (this.fh / 8)
            );
        }
    }
}
