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
    private HashMap<String, Image> emojis;
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

    public Image getEmoji(String emoji) {
        return this.emojis.get(emoji);
    }

    /**
     * Get currently matched emoji and resets position.
     * @throws NullPointerException
     * @return
     */
    public Image getEmoji() {
        String value = this.currentNode.getValue();
        Image image = this.emojis.get(value);
        this.resetState();
        return image;
    }

    public void resetState() {
        this.currentNode = this.emojiTree;
    }

    /**
     * >> Null if no match.
     * @param c
     * @return
     */
    public Integer match(char c) {
        var child = this.currentNode.getChild(c);
        if (child != null) {
            this.currentNode = child;

            if (child.getValue() == null) {
                return null;
            }

            return child.getHeight();
        }

        if (this.currentNode != this.emojiTree) {
            this.currentNode = this.emojiTree;
            child = this.emojiTree.getChild(c);
            if (child != null) {
                if (child.getValue() == null) {
                    return null;
                }

                return 1;
            }
        }

        return null;
    }

    public void loadResources(String rootDir) throws IOException {
        InputStream defnStream = this.getClass().getResourceAsStream(rootDir + "/definitions.toml");
        // We need to force UTF-8 encoding or else unicode emojis may get mangled.
        String defnString = IOUtils.toString(defnStream, StandardCharsets.UTF_8);
        Toml defn = new Toml().read(defnString);
        List<HashMap<String, String>> emojis = defn.getList("emojis");

        for (var emoji : emojis) {
            String path = emoji.get("path");
            String unicode = emoji.get("unicode");

            InputStream stream = this.getClass().getResourceAsStream(rootDir + "/" + path);
            this.emojis.put(unicode, ImageIO.read(stream));
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
