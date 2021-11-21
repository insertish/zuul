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
    private Font font;
    private int fw, fh;

    public JTerminalView(TerminalEmulator emulator) {
        this.emulator = emulator;
        this.makeFrame();
    }

    public void makeFrame() {
        this.setBackground(Color.BLACK);
        this.loadFont("/VT323-Regular.ttf");

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                JTerminalView view = (JTerminalView) e.getComponent();
                view.repaint();
            }
        });
    }

    public void loadFont(String source) {
        InputStream stream = this.getClass().getResourceAsStream(source);

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, stream)
                .deriveFont(32f);
            
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
    }
}
