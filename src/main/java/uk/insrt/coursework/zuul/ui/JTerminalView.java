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

import com.moandjiezana.toml.Toml;

/**
 * Rendering component of {@link TerminalEmulator}
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class JTerminalView extends JPanel {
    private TerminalEmulator emulator;
    
    private EmojiManager emojiManager;
    private Font derivedFont;
    private Font font;

    private Thread blinkThread;
    private boolean blinkState;

    private float fw, fh, foffset, fratio;

    /**
     * Construct a TerminalView
     * @param emulator Terminal emulator this view belongs to
     */
    public JTerminalView(TerminalEmulator emulator) {
        this.emulator = emulator;
        this.emojiManager = new EmojiManager();
        this.blinkState = false;
        this.loadResources();
        this.makeFrame();
    }

    /**
     * Prepare the terminal view for rendering.
     */
    public void makeFrame() {
        var view = this;
        this.setBackground(Color.BLACK);

        // Register a listener to repaint and adjust measurements when resizing window.
        // https://stackoverflow.com/a/8917978
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                view.setBackground(Color.BLACK);
                view.deriveFont();
                view.repaint();
            }
        });

        // Start a new thread for blinking the cursor.
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

    /**
     * Load any resources required by the terminal view to render itself properly.
     */
    public void loadResources() {
        try {
            InputStream stream = this.getClass().getResourceAsStream("/emulator.toml");
            Toml defn = new Toml().read(stream);

            // If a font is defined, load it.
            String font = defn.getString("font");
            if (font != null) {
                this.loadFont(font, 32.0f / 12.8f);
            }

            // If an emoji root directory is defined, load it.
            String rootDir = defn.getString("emojis");
            if (rootDir != null) {
                this.emojiManager.loadResources(rootDir);
            }
        } catch (Exception e) {
            System.err.println("Failed to load any resources for terminal view!");
            e.printStackTrace();
        }
    }

    /**
     * Load a specific font with a known ratio.
     * We expect this font to be monoscape.
     * @param source Path to the font to be loaded
     * @param ratio Ratio of width to height for this font
     * @throws IOException if the font cannot be loaded from a given path
     * @throws FontFormatException if the font is of an incorrect format, we expect a TTF
     */
    public void loadFont(String source, float ratio) throws IOException, FontFormatException {
        InputStream stream = this.getClass().getResourceAsStream(source);
        this.fratio = ratio;
        this.font = Font.createFont(Font.TRUETYPE_FONT, stream);
    }

    /**
     * Derive the font measurements before we continue rendering.
     */
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

    /**
     * Dispose of this terminal view.
     */
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
        // https://stackoverflow.com/a/17922749
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
            int skipChars = 0;
            for (int x=0;x<buffer.getWidth();x++) {
                // If needs be, skip chars in this line.
                if (skipChars > 0) {
                    skipChars--;
                    continue;
                }

                // Get the character to render.
                char c = buffer.getChar(x, y);

                // Match each char for emoji codepoints.
                // If we start to match an emoji, peek ahead.
                int emojiMatch = this.emojiManager.match(c);
                int offset = 0;
                while (emojiMatch == EmojiManager.MATCH_SOME) {
                    emojiMatch = this.emojiManager.match(buffer.getChar(x + ++offset, y));
                }

                // Get this cell's background and foreground colours.
                Color bg = buffer.getBg(x, y);
                Color fg = buffer.getFg(x, y);

                // Find this char's offset.
                int drawX = Math.round(ox + this.fw * x);
                int drawY = Math.round(oy + this.fh * y);

                // Draw rect if there's a background present.
                if (bg != null && bg != Color.BLACK) {
                    g.setColor(bg);
                    g.fillRect(drawX, drawY, (int) Math.ceil(this.fw), (int) Math.ceil(this.fh));
                }

                // If we're drawing an emoji, get the image and skip text.
                if (emojiMatch == EmojiManager.MATCH_FOUND) {
                    Emoji emoji = this.emojiManager.getEmoji();
                    g.drawImage(
                        emoji.getImage(),
                        drawX, drawY,
                        Math.round(this.fw * emoji.getWidth()),
                        Math.round(this.fh * emoji.getHeight()),
                        this
                    );

                    skipChars = offset;
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

        // Fire draw event for custom rendering.
        this.emulator.getEventSystem().emit(new EventDraw(g, ox, oy, this.fw, this.fh));
    }
}
