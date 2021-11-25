package uk.insrt.coursework.zuul.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

public class JTerminalView extends JPanel {
    private TerminalEmulator emulator;
    
    private EmojiManager emojiManager;
    private Font derivedFont;
    private Font font;

    private Thread blinkThread;
    private boolean blinkState;

    private float fw, fh, foffset, fratio;

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
                view.deriveFont();
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
        try {
            this.loadFont("/VT323-Regular.ttf", 32.0f / 12.8f);
            this.emojiManager.loadResources();
        } catch (Exception e) {
            System.err.println("Failed to load resources for terminal view!");
            e.printStackTrace();
        }
    }

    public void loadFont(String source, float ratio) throws IOException, FontFormatException {
        InputStream stream = this.getClass().getResourceAsStream(source);
        this.fratio = ratio;
        this.font = Font.createFont(Font.TRUETYPE_FONT, stream);
    }

    public void deriveFont() {
        final int padding = 100;

        // To find the height of the font, we take the smallest
        // side of the window height or the proportional height
        // found from the window width, and then we divide it
        // by our fixed terminal height.
        float height = Math.min(
            this.fratio *
                  (float) (this.getWidth() - padding)
                / (float) TerminalEmulator.TERMINAL_WIDTH,
            (this.getHeight() - padding)
                / (float) TerminalEmulator.TERMINAL_HEIGHT
        );

        this.derivedFont = this.font.deriveFont(height);
    
        // The FontRenderContext is used to determine the font dimensions
        var frc = new FontRenderContext(new AffineTransform(), true, true);
        var bounds = this.derivedFont.getStringBounds(" ", frc);
        
        this.fw = (float) bounds.getWidth();
        this.fh = (float) bounds.getHeight();

        // We need to find the distance between the baseline
        // and the ascender so we can properly align everything.
        // https://docs.oracle.com/javase/tutorial/2d/text/fontconcepts.html
        this.foffset = this.derivedFont.getLineMetrics(" ", frc).getAscent();
    }

    public void dispose() {
        // We need to kill the blind thread when disposing
        // of the UI, but that's not possible so instead I
        // am interrupting the thread, catching that and
        // exiting out peacefully.
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
        
        // Setup our canvas for rendering.
        g.setColor(Color.BLACK);
        g.setFont(this.derivedFont);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Find our topleft-most (x,y) to start rendering from.
        TextBuffer buffer = this.emulator.getBuffer();
        float ox = (this.getWidth() - this.fw * buffer.getWidth()) / 2;
        float oy = (this.getHeight() - this.fh * buffer.getHeight()) / 2;

        // Render each cell individually.
        for (int y=0;y<buffer.getHeight();y++) {
            int offsetX = 0;
            for (int x=0;x<buffer.getWidth();x++) {
                char c = buffer.getChar(x, y);

                // Match each char for emoji codepoints.
                Integer emojiMatch = this.emojiManager.match(c);
                if (emojiMatch != null) {
                    offsetX += emojiMatch - 1;
                }

                // Get this cell's background and foreground colours.
                Color bg = buffer.getBg(x, y);
                Color fg = buffer.getFg(x, y);

                // Find this char's offset.
                int drawX = Math.round(ox + this.fw * (x - offsetX));
                int drawY = Math.round(oy + this.fh * y);

                // Draw rect if there's a background present.
                if (bg != null && bg != Color.BLACK) {
                    g.setColor(bg);
                    g.fillRect(drawX, drawY, Math.round(this.fw), Math.round(this.fh));
                }

                // If we're drawing an emoji, get the image and skip text.
                if (emojiMatch != null) {
                    g.drawImage(
                        this.emojiManager.getEmoji(),
                        drawX, drawY,
                        Math.round(this.fw),
                        Math.round(this.fh),
                        this
                    );
                    continue;
                }

                // Drawing the char if it's not a space, we have to
                // take care to add the offset we previously found or
                // otherwise the distance between the baseline and
                // the ascender. This is because Graphics.drawString
                // draws text from the leftmost baseline equal to (x,y).
                if (c != 0 && c != ' ') {
                    g.setColor(fg);
                    g.drawString(
                        String.valueOf(c),
                        drawX,
                        Math.round(drawY + this.foffset)
                    );
                }
            }

            this.emojiManager.resetState();
        }

        // Draw a blinker to indicate the user can type here.
        // Offset it a bit to not interfere with any text on-screen.
        if (this.blinkState) {
            g.setColor(Color.WHITE);
            g.fillRect(
                (int) (ox + this.fw * buffer.getPosX() + 1),
                (int) (oy + this.fh * buffer.getPosY() + this.foffset),
                (int) this.fw - 2,
                (int) (this.fh / 8)
            );
        }
    }
}
