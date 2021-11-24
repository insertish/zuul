package uk.insrt.coursework.zuul.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class JTerminalView extends JPanel {
    private TerminalEmulator emulator;
    
    private EmojiManager emojiManager;
    private Image temp;
    private Font font;

    private Thread blinkThread;
    private boolean blinkState;

    private int fw, fh;

    public JTerminalView(TerminalEmulator emulator) {
        this.emulator = emulator;
        this.emojiManager = new EmojiManager();
        this.blinkState = false;
        this.loadResources();
        this.makeFrame();
    }

    public void makeFrame() {
        this.setBackground(Color.BLACK);

        var view = this;
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                view.repaint();
            }
        });

        this.blinkThread = new Thread("Blink Thread") {
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

        this.blinkThread.start();
    }

    public void loadResources() {
        this.loadFont("/VT323-Regular.ttf");
        
        try {
            this.emojiManager.loadResources();
            InputStream stream = this.getClass().getResourceAsStream("/emojis/trol.png");
            this.temp = ImageIO.read(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void dispose() {
        // https://docs.oracle.com/javase/1.5.0/docs/guide/misc/threadPrimitiveDeprecation.html
        this.blinkThread.interrupt();
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

            g.drawImage(this.temp, ox + this.fw * buffer.getPosX(), oy + this.fh * (buffer.getPosY() + 1), 64, 64, Color.BLACK, this);
        }
    }
}
