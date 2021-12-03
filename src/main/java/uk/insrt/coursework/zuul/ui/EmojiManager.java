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

/**
 * Class which helps manage loading and resolving Emojis.
 * 
 * @author Pawel Makles (K21002534)
 * @version 1.0-SNAPSHOT
 */
public class EmojiManager {
    private HashMap<String, Emoji> emojis;
    private Tree<Character, String> emojiTree;
    private Tree<Character, String> currentNode;

    /**
     * Construct a new EmojiManager.
     */
    public EmojiManager() {
        this.emojis = new HashMap<>();
        this.emojiTree = new Tree<>();
        this.currentNode = this.emojiTree;
    }

    /**
     * Check whether a given Emoji is present in this manager.
     * @param emoji Unicode representation of Emoji
     * @return True if the Emoji is available
     */
    public boolean hasEmoji(String emoji) {
        return this.emojis.containsKey(emoji);
    }

    /**
     * Get an Emoji by its Unicode representation.
     * @param emoji Unicode representation of Emoji
     * @return The Emoji or null if it doesn't exist
     */
    public Emoji getEmoji(String emoji) {
        return this.emojis.get(emoji);
    }

    /**
     * Get currently matched emoji and resets position.
     * @return Emoji if it was found, or null if not
     */
    public Emoji getEmoji() {
        String value = this.currentNode.getValue();
        Emoji emoji = this.emojis.get(value);
        this.resetState();
        return emoji;
    }

    /**
     * Reset the state of the matching mechanism.
     */
    public void resetState() {
        this.currentNode = this.emojiTree;
    }

    /**
     * The matching mechanism has not matched any characters to potential emojis.
     */
    public static final int MATCH_NONE = 0;

    /**
     * The matching mechanism has matched some characters to potential emojis.
     */
    public static final int MATCH_SOME = 1;

    /**
     * The matching mechanism has matched an emoji.
     */
    public static final int MATCH_FOUND = 2;

    /**
     * Match the next character.
     * @param c Character to match against
     * @return One of {@link #MATCH_NONE}, {@link #MATCH_SOME} or {@link #MATCH_FOUND}
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

    /**
     * Load emoji definitions and resources from a given resource directory.
     * @param rootDir Root directory at which we expect a valid {@code definitions.toml} to exist
     * @throws IOException if the definition file is missing or defined emojis are invalid
     */
    public void loadResources(String rootDir) throws IOException {
        InputStream defnStream = this.getClass().getResourceAsStream(rootDir + "/definitions.toml");
        // We need to force UTF-8 encoding or else unicode emojis may get mangled.
        String defnString = IOUtils.toString(defnStream, StandardCharsets.UTF_8);
        Toml defn = new Toml().read(defnString);
        List<HashMap<String, Object>> emojis = defn.getList("emojis");

        // Load each emoji in sequence
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
