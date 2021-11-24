package uk.insrt.coursework.zuul.ui;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.moandjiezana.toml.Toml;

public class EmojiManager {
    private HashMap<String, Image> emojis;

    public EmojiManager() {
        this.emojis = new HashMap<>();
    }

    public boolean hasEmoji(String emoji) {
        return this.emojis.containsKey(emoji);
    }

    public Image getEmoji(String emoji) {
        return this.emojis.get(emoji);
    }

    public void loadResources() throws IOException {
        InputStream defnStream = this.getClass().getResourceAsStream("/emojis/definitions.toml");
        Toml defn = new Toml().read(defnStream);
        List<HashMap<String, String>> emojis = defn.getList("emojis");

        for (var emoji : emojis) {
            String path = emoji.get("path");
            String unicode = emoji.get("unicode");

            InputStream stream = this.getClass().getResourceAsStream("/emojis/" + path);
            this.emojis.put(unicode, ImageIO.read(stream));
        }
    }
}
