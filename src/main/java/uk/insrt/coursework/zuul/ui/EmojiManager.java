package uk.insrt.coursework.zuul.ui;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.moandjiezana.toml.Toml;

import org.apache.commons.io.IOUtils;

import uk.insrt.coursework.zuul.util.Tree;

public class EmojiManager {
    private HashMap<String, Emoji> emojis;
    private Tree<Character, String> emojiTree;
    private Tree<Character, String> currentNode;

    public EmojiManager() {
        this.emojis = new HashMap<>();
        this.emojiTree = new Tree<>();
        this.currentNode = this.emojiTree;
    }

    public boolean hasEmoji(String emoji) {
        return this.emojis.containsKey(emoji);
    }

    public Emoji getEmoji(String emoji) {
        return this.emojis.get(emoji);
    }

    /**
     * Get currently matched emoji and resets position.
     * @throws NullPointerException
     * @return
     */
    public Emoji getEmoji() {
        String value = this.currentNode.getValue();
        Emoji emoji = this.emojis.get(value);
        this.resetState();
        return emoji;
    }

    public void resetState() {
        this.currentNode = this.emojiTree;
    }

    public static final int MATCH_NONE = 0;
    public static final int MATCH_SOME = 1;
    public static final int MATCH_FOUND = 2;

    /**
     * >> Null if no match.
     * @param c
     * @return
     */
    public int match(char c) {
        var child = this.currentNode.getChild(c);
        if (child != null) {
            this.currentNode = child;
            if (child.getValue() == null) return MATCH_SOME;
            return MATCH_FOUND;
        }

        if (this.currentNode != this.emojiTree) {
            this.currentNode = this.emojiTree;
            child = this.emojiTree.getChild(c);
            if (child != null) {
                if (child.getValue() != null) return MATCH_SOME;
                return MATCH_FOUND;
            }
        }

        return MATCH_NONE;
    }

    public void loadResources(String rootDir) throws IOException {
        InputStream defnStream = this.getClass().getResourceAsStream(rootDir + "/definitions.toml");
        // We need to force UTF-8 encoding or else unicode emojis may get mangled.
        String defnString = IOUtils.toString(defnStream, StandardCharsets.UTF_8);
        Toml defn = new Toml().read(defnString);
        List<HashMap<String, Object>> emojis = defn.getList("emojis");

        for (var emoji : emojis) {
            String path = (String) emoji.get("path");
            String unicode = (String) emoji.get("unicode");

            InputStream stream = this.getClass().getResourceAsStream(rootDir + "/" + path);
            Image image = ImageIO.read(stream);

            Emoji newEmoji;
            if (emoji.containsKey("width") && emoji.containsKey("height")) {
                int width = ((Long) emoji.get("width")).intValue();
                int height = ((Long) emoji.get("height")).intValue();
                newEmoji = new Emoji(image, width, height);
            } else {
                newEmoji = new Emoji(image, unicode);
            }

            this.emojis.put(unicode, newEmoji);
            this.emojiTree.addChildWithPath(
                new ArrayList<>(Arrays.asList(
                    unicode.chars()
                        .mapToObj(c -> (char)c)
                        .toArray(Character[]::new))),
                unicode
            );
        }
    }
}
